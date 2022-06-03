package com.epam.tkach.carrent.model.entity;

import java.time.LocalDateTime;
import java.util.Date;

public class Transaction extends Entity {
    private User user;
    private String description;
    private double sum;
    private LocalDateTime timestamp;
    //Constructor

    public Transaction() {
    }

    //Getters
    public User getUser() {
        return user;
    }

    public String getDescription() {
        return description;
    }

    public double getSum() {
        return sum;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    //Setters

    public void setUser(User user) {
        this.user = user;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "user=" + user +
                ", description='" + description + '\'' +
                ", sum=" + sum +
                ", timestamp=" + timestamp +
                ", ID=" + ID +
                '}';
    }
}
