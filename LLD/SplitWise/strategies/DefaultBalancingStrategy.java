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
    public List<Payment> getPendingPaymentsForUser(User user) {
        List<Payment> userPendingPayments = new ArrayList<>();
        Map<User, Double> userNetBalanceMap = new HashMap<>();

        for (List<Payment> pendingPayments : groupToPendingPaymentsMap.values()) {
            for (Payment payment : pendingPayments) {
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
                userPendingPayments.add(new Payment(user, otherUser, netAmount));
            } else if (netAmount < 0) {
                userPendingPayments.add(new Payment(otherUser, user, -netAmount));
            }
        }
        return userPendingPayments;
    }

    @Override
    public List<Payment> getPendingPaymentsForUsers(User user1, User user2) {
        List<Payment> userPendingPayments = new ArrayList<>();
        Map<User, Double> userNetBalanceMap = new HashMap<>();

        for (List<Payment> pendingPayments : groupToPendingPaymentsMap.values()) {
            for (Payment payment : pendingPayments) {
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
                userPendingPayments.add(new Payment(user1, otherUser, netAmount));
            } else if (netAmount < 0) {
                userPendingPayments.add(new Payment(otherUser, user1, -netAmount));
            }
        }
        return userPendingPayments;
    }

    @Override
    public List<Payment> getAllPendingPaymentsForUserInGroup(User user, Group group) {
        List<Payment> pendingPayments = groupToPendingPaymentsMap.getOrDefault(group.getId(), new ArrayList<>());
        return pendingPayments.stream()
                .filter(payment -> payment.getFromUser().equals(user) || payment.getToUser().equals(user))
                .collect(Collectors.toList());
    }
}
