package strategies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import models.Group;
import models.Payment;
import models.User;

public class DefaultBalancingStrategy implements IBalancingStrategy {
    private final Map<String, List<Payment>> groupToPaymentsMap;

    public DefaultBalancingStrategy() {
        this.groupToPaymentsMap = new HashMap<>();
    }

    @Override
    public void balancePayments(Payment payment) {
        if (payment == null || payment.getGroup() == null) {
            return;
        }

        String groupId = payment.getGroup().getId();
        if (!groupToPaymentsMap.containsKey(groupId)) {
            groupToPaymentsMap.put(groupId, new ArrayList<>());
        }

        List<Payment> payments = groupToPaymentsMap.get(groupId);
        if (payments.isEmpty()) {
            payments.add(payment);
            return;
        }

        for (int i = 0; i < payments.size(); i++) {
            Payment existingPayment = payments.get(i);
            if (existingPayment.getFromUser().equals(payment.getToUser()) &&
                    existingPayment.getToUser().equals(payment.getFromUser())) {
                double newAmount = existingPayment.getAmount() - payment.getAmount();
                if (newAmount > 0) {
                    existingPayment.setAmount(newAmount);
                } else if (newAmount < 0) {
                    existingPayment.setFromUser(payment.getFromUser());
                    existingPayment.setToUser(payment.getToUser());
                    existingPayment.setAmount(-newAmount);
                } else {
                    payments.remove(i);
                }
                return;
            } else if (existingPayment.getFromUser().equals(payment.getFromUser()) &&
                    existingPayment.getToUser().equals(payment.getToUser())) {
                existingPayment.setAmount(existingPayment.getAmount() + payment.getAmount());
                return;
            }
        }

        payments.add(payment);
    }

    @Override
    public List<Payment> getNetPaymentsForUser(User user) {
        List<Payment> userNetPayments = new ArrayList<>();
        Map<User, Double> userNetBalanceMap = new HashMap<>();

        for (List<Payment> payments : groupToPaymentsMap.values()) {
            for (Payment payment : payments) {
                if (payment.getFromUser().equals(user)) {
                    userNetBalanceMap.put(payment.getToUser(),
                            userNetBalanceMap.getOrDefault(payment.getToUser(), 0.0) + payment.getAmount());
                } else if (payment.getToUser().equals(user)) {
                    userNetBalanceMap.put(payment.getFromUser(),
                            userNetBalanceMap.getOrDefault(payment.getFromUser(), 0.0) - payment.getAmount());
                }
            }
        }

        for (Map.Entry<User, Double> entry : userNetBalanceMap.entrySet()) {
            User otherUser = entry.getKey();
            double netAmount = entry.getValue();
            if (netAmount > 0) {
                userNetPayments.add(new Payment(user, otherUser, netAmount));
            } else if (netAmount < 0) {
                userNetPayments.add(new Payment(otherUser, user, -netAmount));
            }
        }
        return userNetPayments;
    }

    @Override
    public List<Payment> getNetPaymentsForUsers(User user1, User user2) {
        List<Payment> userNetPayments = new ArrayList<>();
        Map<User, Double> userNetBalanceMap = new HashMap<>();

        for (List<Payment> payments : groupToPaymentsMap.values()) {
            for (Payment payment : payments) {
                if (payment.getFromUser().equals(user1) && payment.getToUser().equals(user2)) {
                    userNetBalanceMap.put(payment.getToUser(),
                            userNetBalanceMap.getOrDefault(payment.getToUser(), 0.0) + payment.getAmount());
                } else if (payment.getToUser().equals(user1) && payment.getFromUser().equals(user2)) {
                    userNetBalanceMap.put(payment.getFromUser(),
                            userNetBalanceMap.getOrDefault(payment.getFromUser(), 0.0) - payment.getAmount());
                }
            }
        }

        for (Map.Entry<User, Double> entry : userNetBalanceMap.entrySet()) {
            User otherUser = entry.getKey();
            double netAmount = entry.getValue();
            if (netAmount > 0) {
                userNetPayments.add(new Payment(user1, otherUser, netAmount));
            } else if (netAmount < 0) {
                userNetPayments.add(new Payment(otherUser, user1, -netAmount));
            }
        }
        return userNetPayments;
    }

    @Override
    public List<Payment> getAllNetPaymentsForUserInGroup(User user, Group group) {
        List<Payment> groupPayments = groupToPaymentsMap.getOrDefault(group.getId(), new ArrayList<>());
        return groupPayments.stream()
                .filter(payment -> payment.getFromUser().equals(user) || payment.getToUser().equals(user))
                .collect(Collectors.toList());
    }
}
