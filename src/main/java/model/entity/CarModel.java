package model.entity;

import java.io.Serializable;

public class CarModel extends Entity implements Serializable {
    CarBrand brand;
    String modelName;

    //Setters
    public void setBrand(CarBrand brand) {
        this.brand = brand;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    //Getters
    public CarBrand getBrand() {
        return brand;
    }

    public String getModelName() {
        return modelName;
    }

    //Constructors
    public CarModel(long ID, CarBrand brand, String modelName) {
        this.ID = ID;
        this.brand = brand;
        this.modelName = modelName;
    }
}
