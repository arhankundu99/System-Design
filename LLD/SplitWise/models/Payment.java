package models;

import java.util.UUID;

public class Payment {
    private String id;
    private User fromUser;
    private User toUser;
    private double amount;
    private Group group;

    public Payment(User fromUser, User toUser, double amount) {
        this(fromUser, toUser, amount, null);
    }

    public Payment(User fromUser, User toUser, double amount, Group group) {
        this.id = UUID.randomUUID().toString();
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.amount = amount;
        this.group = group;
    }

    public String getId() {
        return id;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Payment clone() {
        return new Payment(this.fromUser, this.toUser, this.amount, this.group);
    }

    @Override
    public String toString() {
        String groupName = group != null ? group.getName() : "N/A";
        return "Payment[from=" + fromUser.getName() + ", to=" + toUser.getName() + ", amount=" + amount + ", group=" + groupName + "]";
    }
}
