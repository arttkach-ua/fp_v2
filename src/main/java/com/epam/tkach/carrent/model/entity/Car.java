package com.epam.tkach.carrent.model.entity;

import com.epam.tkach.carrent.model.entity.enums.BodyStyles;
import com.epam.tkach.carrent.model.entity.enums.CarClass;
import com.epam.tkach.carrent.model.entity.enums.FuelTypes;
import com.epam.tkach.carrent.model.entity.enums.TransmissionTypes;

import javax.validation.constraints.NotNull;

public class Car extends Entity{
    @NotNull(message = "error.nullCarBrand")
    CarBrand brand;
    @NotNull(message = "error.nullCarModel")
    CarModel model;
    @NotNull(message = "error.nullCarClass")
    CarClass carClass;
    @NotNull(message = "error.nullGraduationYear")
    int graduationYear;
    @NotNull(message = "error.nullBodyStyle")
    BodyStyles bodyStyle;//!!!!!
    @NotNull(message = "error.nullTransmissionType")
    TransmissionTypes transmission;
    @NotNull(message = "error.nullFuelType")
    FuelTypes fuelType;
    String stateNumber;
    String vinCode;
    double price;

    double engine;
    //Getters
    public CarBrand getBrand() {
        return brand;
    }

    public CarModel getModel() {
        return model;
    }

    public CarClass getCarClass() {
        return carClass;
    }

    public int getGraduationYear() {
        return graduationYear;
    }

    public BodyStyles getBodyStyle() {
        return bodyStyle;
    }

    public TransmissionTypes getTransmission() {
        return transmission;
    }

    public FuelTypes getFuelType() {
        return fuelType;
    }

    public String getStateNumber() {
        return stateNumber;
    }

    public String getVinCode() {
        return vinCode;
    }

    public double getEngine() {
        return engine;
    }

    public double getPrice() {
        return price;
    }
    //Setters

    public void setBrand(CarBrand brand) {
        this.brand = brand;
    }

    public void setModel(CarModel model) {
        this.model = model;
    }

    public void setCarClass(CarClass carClass) {
        this.carClass = carClass;
    }

    public void setGraduationYear(int graduationYear) {
        this.graduationYear = graduationYear;
    }

    public void setBodyStyle(BodyStyles bodyStyle) {
        this.bodyStyle = bodyStyle;
    }

    public void setTransmission(TransmissionTypes transmission) {
        this.transmission = transmission;
    }

    public void setFuelType(FuelTypes fuelType) {
        this.fuelType = fuelType;
    }

    public void setStateNumber(String stateNumber) {
        this.stateNumber = stateNumber;
    }

    public void setVinCode(String vinCode) {
        this.vinCode = vinCode;
    }

    public void setEngine(double engine) {
        this.engine = engine;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    //Constructor

    public Car() {
    }

    public Car(CarBrand brand, CarModel model, CarClass carClass, int graduationYear, BodyStyles bodyStyle, TransmissionTypes transmission, FuelTypes fuelType, String stateNumber, String vinCode, double engine, double price) {
        this.brand = brand;
        this.model = model;
        this.carClass = carClass;
        this.graduationYear = graduationYear;
        this.bodyStyle = bodyStyle;
        this.transmission = transmission;
        this.fuelType = fuelType;
        this.stateNumber = stateNumber;
        this.vinCode = vinCode;
        this.engine = engine;
        this.price = price;
    }
}
