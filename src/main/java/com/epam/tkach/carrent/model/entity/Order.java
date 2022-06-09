package com.epam.tkach.carrent.model.entity;

import com.epam.tkach.carrent.model.entity.enums.OrderStatuses;
import com.mysql.cj.xdevapi.DeleteStatement;

import javax.validation.constraints.NotNull;

public class Order extends Entity{
    @NotNull
    private User client;
    @NotNull
    private OrderStatuses status;
    @NotNull
    private Car car;
    private int daysCount;
    private double price;
    private double driverPrice;
    private String documents;
    private User manager;
    private double rentSum;
    private String managerComment;
    private boolean withDriver;

    //Getters
    public User getClient() {
        return client;
    }
    public OrderStatuses getStatus() {
        return status;
    }
    public Car getCar() {
        return car;
    }
    public int getDaysCount() {
        return daysCount;
    }
    public double getPrice() {
        return price;
    }
    public double getDriverPrice() {
        return driverPrice;
    }
    public String getDocuments() {
        return documents;
    }
    public User getManager() {
        return manager;
    }
    public double getRentSum() {
        return rentSum;
    }
    public String getManagerComment() {
        return managerComment;
    }
    public boolean isWithDriver() {
        return withDriver;
    }

    //Setters
    public void setClient(User client) {
        this.client = client;
    }
    public void setStatus(OrderStatuses status) {
        this.status = status;
    }
    public void setCar(Car car) {
        this.car = car;
    }
    public void setDaysCount(int daysCount) {
        this.daysCount = daysCount;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setDriverPrice(double driverPrice) {
        this.driverPrice = driverPrice;
    }
    public void setDocuments(String documents) {
        this.documents = documents;
    }
    public void setManager(User manager) {
        this.manager = manager;
    }
    public void setRentSum(double rentSum) {
        this.rentSum = rentSum;
    }
    public void setManagerComment(String managerComment) {
        this.managerComment = managerComment;
    }
    public void setWithDriver(boolean withDriver) {
        this.withDriver = withDriver;
    }
    //Constructor

    public Order() {
    }

    public void calculateSum(){
        if (withDriver){
            rentSum = daysCount*(driverPrice + price);
        }else {
            rentSum = daysCount*price;
        }
    }



}
