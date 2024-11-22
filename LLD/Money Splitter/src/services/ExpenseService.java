package services;

import exceptions.InvalidStateException;
import models.Expense;
import models.Settlement;
import models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseService {
    Map<String, Expense> expensesMap;
    public ExpenseService() {
        expensesMap = new HashMap<>();
    }

    public void addExpense(String groupId, String name, String desc, double amount, User createdBy, List<User> lentTo) {
        Expense expense = new Expense(groupId, name, desc, amount, createdBy, lentTo);
        expensesMap.put(expense.getId(), expense);
    }

    public Expense getExpenseById(String id) {
        return expensesMap.get(id);
    }

    public List<Expense> getAllExpensesByGroupId(String groupId) {
        List<Expense> expenses = new ArrayList<>();
        for (Expense expense : expensesMap.values()) {
            if (expense.getGroupID().equals(groupId)) {
                expenses.add(expense);
            }
        }
        return expenses;
    }

    public List<Expense> getAllExpensesByUserId(String userId) {
        List<Expense> expenses = new ArrayList<>();
        for (Expense expense : expensesMap.values()) {
            if (expense.getLentBy().getId().equals(userId)) {
                expenses.add(expense);
            } else {
                for (User user : expense.getLentTo()) {
                    if (user.getId().equals(userId)) {
                        expenses.add(expense);
                    }
                }
            }
        }
        return expenses;
    }

    public void settleExpenseById(String id) throws InvalidStateException {
        Expense expense = expensesMap.get(id);
        expense.settle();
    }

    public void settleAllExpensesByGroupId(String groupId) throws InvalidStateException {
        List<Expense> expenses = getAllExpensesByGroupId(groupId);
        for (Expense expense : expenses) {
            expense.settle();
        }
    }

    public List<Settlement> getPendingSettlementsByGroupIdV1(String groupId) {
        List<Settlement> settlements = new ArrayList<>();
        List<Expense> expenses = getAllExpensesByGroupId(groupId);

        Map<User, Map<User, Double>> paymentsMap = new HashMap<>();
        for (Expense expense : expenses) {
            if (expense.isSettled()) {
                continue;
            }
            if (!paymentsMap.containsKey(expense.getLentBy())) {
                paymentsMap.put(expense.getLentBy(), new HashMap<>());
            }
            double amount = expense.getAmount();
            double share = amount / (expense.getLentTo().size() + 1);
            for (User user : expense.getLentTo()) {

                if (!paymentsMap.get(expense.getLentBy()).containsKey(user)) {
                    paymentsMap.get(expense.getLentBy()).put(user, 0.0);
                }

                paymentsMap.get(expense.getLentBy()).put(user, paymentsMap.get(expense.getLentBy()).get(user) + share);
            }
        }

        for (User from : paymentsMap.keySet()) {
            for (User to: paymentsMap.get(from).keySet()) {
                double amount = paymentsMap.get(from).get(to);
                Settlement settlement = new Settlement(from, to, amount);
                settlements.add(settlement);
            }
        }

        return settlements;
    }
}
