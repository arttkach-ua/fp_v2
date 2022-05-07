package model.entity;

import java.io.Serializable;

public class CarBrand extends Entity implements Serializable {
    String carBrandName;

    //Getters
    public String getCarBrandName() {
        return carBrandName;
    }

    //Setters
    public void setCarBrandName(String carBrandName) {
        this.carBrandName = carBrandName;
    }
    //Constructor
    public CarBrand(long ID, String carBrandName) {
        this.ID = ID;
        this.carBrandName = carBrandName;
    }
}
