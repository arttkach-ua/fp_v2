package com.epam.tkach.carrent.model.entity;

import com.epam.tkach.carrent.model.entity.enums.InvoiceTypes;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class Invoice extends Entity{
    @NotNull(message = "error.nullClient")
    private User client;
    @NotNull(message = "error.nullOrder")
    private Order order;
    @NotNull(message = "error.nullDescription")
    private String description;
    @Positive(message = "error.nullAmount")
    private double amount;
    @NotNull(message = "error.nullInvoiceType")
    private InvoiceTypes type;
    private boolean paid;
    private Date dateTime;

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

    public InvoiceTypes getType() {
        return type;
    }

    public boolean isPaid() {
        return paid;
    }

    public Date getDateTime() {
        return dateTime;
    }
    public String getFormattedDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy h:mm a");
        return sdf.format(dateTime);
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

    public void setType(InvoiceTypes type) {
        this.type = type;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
    //Constructor

    public Invoice() {
    }
}
