package models;

import exceptions.InvalidStateException;

import java.util.List;
import java.util.UUID;

public class Expense {
    private final String id;
    private final String groupID;
    private String name;
    private final String description;
    private double amount;
    private final User lentBy;
    private boolean isSettled;
    private final List<User> lentTo;

    public Expense(String groupID, String name, String description, double amount, User lentBy, List<User> lentTo) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.groupID = groupID;
        this.description = description;
        this.amount = amount;
        this.lentBy = lentBy;
        this.lentTo = lentTo;
    }

    public String getId() {
        return id;
    }
    public String getGroupID() {
        return groupID;
    }
    public String getDescription() {
        return description;
    }
    public double getAmount() {
        return amount;
    }
    public User getLentBy() {
        return lentBy;
    }

    public boolean isSettled() {
        return isSettled;
    }

    public void settle() throws InvalidStateException {
        if (this.isSettled) {
            throw new InvalidStateException("Expense is already settled!");
        }
        this.isSettled = true;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public List<User> getLentTo() {
        return lentTo;
    }
}
