package strategies;

import java.util.List;

import models.Group;
import models.Payment;
import models.User;

public interface IBalancingStrategy {
    void balancePayments(Payment payment);
    List<Payment> getNetPaymentsForUser(User user);
    List<Payment> getNetPaymentsForUsers(User user1, User user2);
    List<Payment> getAllNetPaymentsForUserInGroup(User user, Group group);
}
