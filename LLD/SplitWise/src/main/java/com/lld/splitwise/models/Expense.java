package com.lld.splitwise.models;

import java.util.UUID;
import com.lld.splitwise.models.User;

import java.util.ArrayList;
import java.util.List;

public class Expense {
    private final int id;
    private final int groupId;
    private final int amount;
    private final String currency;
    private final User from;
    private final List<User> toUserIds;

    private Expense(Builder builder) {
        this.id = builder.id;
        this.groupId = builder.groupId;
        this.amount = builder.amount;
        this.currency = builder.currency;
        this.from = builder.from;
        this.toUserIds = builder.toUserIds;
    }

    public static class Builder {
        private int id;
        private int groupId;
        private int amount;
        private String currency;
        private User from;
        private List<User> toUserIds = new ArrayList<>();

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setGroupId(int groupId) {
            this.groupId = groupId;
            return this;
        }

        public Builder setAmount(int amount) {
            this.amount = amount;
            return this;
        }

        public Builder setCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        public Builder setFrom(User from) {
            this.from = from;
            return this;
        }

        public Builder addToUser(User user) {
            if (user != null) {
                this.toUserIds.add(user);
            }
            return this;
        }

        public Builder setToUsers(List<User> toUsers) {
            if (toUsers != null) {
                this.toUserIds = toUsers;
            }
            return this;
        }

        public Expense build() {
            // Optional: Add validation logic here
            if (currency == null || currency.isEmpty()) {
                throw new IllegalStateException("Currency cannot be null or empty");
            }
            if (from == null) {
                throw new IllegalStateException("The 'from' user cannot be null");
            }
            if (toUserIds == null || toUserIds.isEmpty()) {
                throw new IllegalStateException("At least one 'to' user must be specified");
            }
            return new Expense(this);
        }
    }

    // Getters for accessing fields (optional but recommended)
    public int getId() {
        return id;
    }

    public int getGroupId() {
        return groupId;
    }

    public int getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public User getFrom() {
        return from;
    }

    public List<User> getToUserIds() {
        return new ArrayList<>(toUserIds);
    }

    // Optionally, override toString(), equals(), and hashCode() methods
}
