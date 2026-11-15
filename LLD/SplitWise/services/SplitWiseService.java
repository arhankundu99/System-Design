package services;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import models.Expense;
import models.Group;
import models.Payment;
import models.User;
import strategies.IBalancingStrategy;

public class SplitWiseService {
    private final ExpenseService expenseService;
    private final PaymentService paymentService;
    private final IBalancingStrategy balancingStrategy;

    public SplitWiseService(ExpenseService expenseService, PaymentService paymentService,
            IBalancingStrategy balancingStrategy) {
        this.expenseService = Objects.requireNonNull(expenseService, "expenseService");
        this.paymentService = Objects.requireNonNull(paymentService, "paymentService");
        this.balancingStrategy = Objects.requireNonNull(balancingStrategy, "balancingStrategy");
    }

    public Expense createExpense(String description, double amount, User paidBy, Group group, List<User> sharedWith) {
        return expenseService.createExpense(description, amount, paidBy, group, sharedWith);
    }

    public Expense createExpense(String description, double amount, User paidBy, Group group,
            Map<User, Double> userShares) {
        return expenseService.createExpense(description, amount, paidBy, group, userShares);
    }

    public void createPayment(User fromUser, User toUser, double amount, Group group) {
        paymentService.createPayment(fromUser, toUser, amount, group);
    }

    public List<Payment> getNetPaymentsForUser(User user) {
        return balancingStrategy.getNetPaymentsForUser(user);
    }

    public List<Payment> getNetPaymentsForUserInGroup(User user, Group group) {
        return balancingStrategy.getAllNetPaymentsForUserInGroup(user, group);
    }
}
