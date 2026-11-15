package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Expense {
    private String id;
    private String description;
    private double amount;
    private User paidBy;
    private Group group;
    private List<User> sharedWith;
    private Map<User, Double> userShares;

    public Expense(String description, double amount, User paidBy, Group group, List<User> sharedWith) {
        this.id = UUID.randomUUID().toString();
        this.description = description;
        this.amount = amount;
        this.paidBy = paidBy;
        this.group = group;
        this.sharedWith = sharedWith;
        this.userShares = new HashMap<>();
        calculateUserShares();
    }

    public Expense(String description, double amount, User paidBy, Group group, Map<User, Double> userShares) {
        this.id = UUID.randomUUID().toString();
        this.description = description;
        this.amount = amount;
        this.paidBy = paidBy;
        this.group = group;
        this.userShares = userShares;
        this.sharedWith = new ArrayList<>(userShares.keySet());
    }

    private void calculateUserShares() {
        double share = amount / sharedWith.size();
        double paidByShare = amount - share * (sharedWith.size() - 1);
        for (User user : sharedWith) {
            if (user.equals(paidBy)) {
                userShares.put(user, paidByShare);
            } else {
                userShares.put(user, share);
            }
        }
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public User getPaidBy() {
        return paidBy;
    }

    public Group getGroup() {
        return group;
    }

    public List<User> getSharedWith() {
        return sharedWith;
    }

    public Map<User, Double> getUserShares() {
        return userShares;
    }
}
