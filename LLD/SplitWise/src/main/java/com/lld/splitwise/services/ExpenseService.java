package com.lld.splitwise.services;
import com.lld.splitwise.repository.ExpenseRepository;
import com.lld.splitwise.models.Expense;
class ExpenseService {
    ExpenseRepository expenseRepository;
    public ExpenseService() {
        expenseRepository = new ExpenseRepository();
    }
    int addExpense(String name, String desc, int groupId, int fromUserId, List<Integer> toUserIds) {
        Expense expense = new Expense(name, desc, groupId, fromUserId, toUserIds);
        return this.expenseRepository.addExpense(expense);
    }

    int updateExpense(int expenseId, String name, String desc, int groupId, int fromUserId, List<Integer> toUserIds) {

    }

    List<Expense> getExpenses(int groupId) {
        return this.expenseRepository.getExpenses(groupId);
    }

    List<Settlement> getSettlements(int groupId) {
        return this.expenseRepository.getSettlements(groupId);
    }
}