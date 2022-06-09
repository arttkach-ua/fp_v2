package com.epam.tkach.carrent.model.entity;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class Tariff extends Entity{
    @NotNull(message = "error.nullTariffName")
    @Size(min = 2,max = 150,message = "error.nullTariffName")
    private String name;
    @Positive(message = "error.nullRentPriceName")
    private double rentPrice;
    @Positive(message = "error.nullDriverPriceName")
    private double driverPrice;

    //Getters
    public String getName() {
        return name;
    }
    public double getRentPrice() {
        return rentPrice;
    }
    public double getDriverPrice() {
        return driverPrice;
    }
    //Setters
    public void setName(String name) {
        this.name = name;
    }
    public void setRentPrice(double rentPrice) {
        this.rentPrice = rentPrice;
    }
    public void setDriverPrice(double driverPrice) {
        this.driverPrice = driverPrice;
    }
    //Constructors

    public Tariff() {
    }

    @Override
    public String toString() {
        return "Tariff{" +
                "name='" + name + '\'' +
                ", rentPrice=" + rentPrice +
                ", driverPrice=" + driverPrice +
                ", ID=" + ID +
                '}';
    }
}
