package com.epam.tkach.carrent.model.entity;

import com.epam.tkach.carrent.model.entity.enums.InvoceTypes;

import javax.validation.constraints.NotNull;

public class Invoice extends Entity{
    @NotNull
    private User client;
    @NotNull
    private Order order;
    @NotNull
    private String description;

    private double amount;
    @NotNull
    private InvoceTypes type;
    private boolean paid;

    //Getters

    public User getClient() {
        return client;
    }

    public Order getOrder() {
        return order;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public InvoceTypes getType() {
        return type;
    }

    public boolean isPaid() {
        return paid;
    }
    //Setters

    public void setClient(User client) {
        this.client = client;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setType(InvoceTypes type) {
        this.type = type;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
    //Constructor

    public Invoice() {
    }
}
