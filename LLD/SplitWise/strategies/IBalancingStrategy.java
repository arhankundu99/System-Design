package strategies;

import java.util.List;

import models.Group;
import models.Payment;
import models.User;

public interface IBalancingStrategy {
    void balancePayments(Payment payment);
    List<Payment> getPendingPaymentsForUser(User user);
    List<Payment> getPendingPaymentsForUsers(User user1, User user2);
    List<Payment> getAllPendingPaymentsForUserInGroup(User user, Group group);
}
