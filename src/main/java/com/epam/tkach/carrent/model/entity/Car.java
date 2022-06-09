package com.epam.tkach.carrent.model.entity;

import com.epam.tkach.carrent.model.entity.enums.BodyStyles;
import com.epam.tkach.carrent.model.entity.enums.CarClass;
import com.epam.tkach.carrent.model.entity.enums.FuelTypes;
import com.epam.tkach.carrent.model.entity.enums.TransmissionTypes;

import javax.validation.constraints.NotNull;

public class Car extends Entity{
    @NotNull(message = "error.nullCarBrand")
    private CarBrand brand;
    @NotNull(message = "error.nullCarModel")
    private CarModel model;
    @NotNull(message = "error.nullCarClass")
    private CarClass carClass;
    @NotNull(message = "error.nullGraduationYear")
    private int graduationYear;
    @NotNull
    private CompleteSet completeSet;
    private String stateNumber;
    private String vinCode;
    @NotNull(message = "error.nullTariff")
    private Tariff tariff;
    private boolean available;

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
    public String getStateNumber() {
        return stateNumber;
    }
    public String getVinCode() {
        return vinCode;
    }
    public boolean isAvailable() {
        return available;
    }
    public Tariff getTariff() {
        return tariff;
    }
    public CompleteSet getCompleteSet() {
        return completeSet;
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
    public void setStateNumber(String stateNumber) {
        this.stateNumber = stateNumber;
    }
    public void setVinCode(String vinCode) {
        this.vinCode = vinCode;
    }
    public void setAvailable(boolean available) {
        this.available = available;
    }
    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }
    public void setCompleteSet(CompleteSet completeSet) {
        this.completeSet = completeSet;
    }
    //Constructor

    public Car() {
    }

    public Car(CarBrand brand, CarModel model, CarClass carClass, int graduationYear, String stateNumber, String vinCode) {
        this.brand = brand;
        this.model = model;
        this.carClass = carClass;
        this.graduationYear = graduationYear;
        this.stateNumber = stateNumber;
        this.vinCode = vinCode;
    }
}
