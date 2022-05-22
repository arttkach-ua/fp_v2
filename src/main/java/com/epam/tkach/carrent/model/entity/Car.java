package com.epam.tkach.carrent.model.entity;

import com.epam.tkach.carrent.model.entity.enums.BodyStyles;
import com.epam.tkach.carrent.model.entity.enums.CarClass;
import com.epam.tkach.carrent.model.entity.enums.FuelTypes;
import com.epam.tkach.carrent.model.entity.enums.TransmissionTypes;

public class Car extends Entity{
    CarBrand brand;
    CarModel model;
    CarClass carClass;
    int graduationYear;
    BodyStyles bodyStyle;//!!!!!
    TransmissionTypes transmission;
    FuelTypes fuelType;
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

    //Constructor

    public Car() {
    }
}
