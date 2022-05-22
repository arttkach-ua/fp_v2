package com.epam.tkach.carrent.model.entity;

import com.epam.tkach.carrent.model.entity.enums.CarClass;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class CarModel extends Entity implements Serializable {
    @NotNull(message = "error.nullCarBrand")
    CarBrand brand;
    @NotNull(message = "error.nullCarClass")
    CarClass carClass;
    @Size(min = 1,max = 150,message = "error.nullCarModelName")
    String modelName;


    //Setters
    public void setBrand(CarBrand brand) {
        this.brand = brand;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public void setCarClass(CarClass carClass) {
        this.carClass = carClass;
    }

    //Getters
    public CarBrand getBrand() {
        return brand;
    }

    public String getModelName() {
        return modelName;
    }

    public CarClass getCarClass() {
        return carClass;
    }

    //Constructors
    public CarModel(long ID, CarBrand brand, String modelName, CarClass carClass) {
        this.ID = ID;
        this.brand = brand;
        this.modelName = modelName;
        this.carClass = carClass;
    }
    public CarModel(){
    }
}
