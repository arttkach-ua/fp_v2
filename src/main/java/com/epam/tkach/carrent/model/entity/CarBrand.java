package com.epam.tkach.carrent.model.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class CarBrand extends Entity implements Serializable {
    @NotNull(message = "error.nullCarBrandName")
    @Size(min = 2,max = 150,message = "error.nullCarBrandName")
    String carBrandName;

    //Getters
    public String getCarBrandName() {
        return carBrandName;
    }

    //Setters
    public void setCarBrandName(String carBrandName) {
        this.carBrandName = carBrandName;
    }
    //Constructors
    public CarBrand() {}

    public CarBrand(long ID, String carBrandName) {
        this.ID = ID;
        this.carBrandName = carBrandName;
    }

}
