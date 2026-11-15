package services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Expense;
import models.Group;
import models.Payment;
import models.User;
import strategies.IBalancingStrategy;

public class ExpenseService {
    private final Map<String, Expense> expensesMap;
    private final IBalancingStrategy balancingStrategy;

    public ExpenseService(IBalancingStrategy balancingStrategy) {
        this.expensesMap = new HashMap<>();
        this.balancingStrategy = balancingStrategy;
    }

    public Expense createExpense(String description, double amount, User paidBy, Group group, List<User> sharedWith) {
        Expense expense = new Expense(description, amount, paidBy, group, sharedWith);
        expensesMap.put(expense.getId(), expense);
        Map<User, Double> userShares = expense.getUserShares();
        for (Map.Entry<User, Double> entry : userShares.entrySet()) {
            User user = entry.getKey();
            double share = entry.getValue();
            if (!user.equals(paidBy)) {
                Payment pendingPayment = new Payment(paidBy, user, share, group);
                balancingStrategy.balancePayments(pendingPayment.clone());
            }
        }
        return expense;
    }

    public Expense createExpense(String description, double amount, User paidBy, Group group, Map<User, Double> userShares) {
        Expense expense = new Expense(description, amount, paidBy, group, userShares);
        expensesMap.put(expense.getId(), expense);
        for (Map.Entry<User, Double> entry : userShares.entrySet()) {
            User user = entry.getKey();
            double share = entry.getValue();
            if (!user.equals(paidBy)) {
                Payment payment = new Payment(user, paidBy, share, group);
                balancingStrategy.balancePayments(payment.clone());
            }
        }
        return expense;
    }

    public Expense getExpenseById(String expenseId) {
        return expensesMap.get(expenseId);
    }
}
