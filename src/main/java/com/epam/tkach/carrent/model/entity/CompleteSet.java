package com.epam.tkach.carrent.model.entity;

import com.epam.tkach.carrent.model.entity.enums.BodyStyles;
import com.epam.tkach.carrent.model.entity.enums.FuelTypes;
import com.epam.tkach.carrent.model.entity.enums.TransmissionTypes;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.StringJoiner;

public class CompleteSet extends Entity{
    @NotNull(message = "error.nullCompleteSetName")
    @Size(min=2)
    private String name;
    @NotNull(message = "error.nullCarModel")
    private CarModel carModel;
    @NotNull(message = "error.nullBodyStyle")
    private BodyStyles bodyStyle;
    @NotNull(message = "error.nullTransmissionType")
    private TransmissionTypes transmission;
    @NotNull(message = "error.nullFuelType")
    private FuelTypes fuelType;
    @Positive(message = "error.nullRentPriceName")
    double engine;

    //Getters
    public BodyStyles getBodyStyle() {
        return bodyStyle;
    }
    public TransmissionTypes getTransmission() {
        return transmission;
    }
    public FuelTypes getFuelType() {
        return fuelType;
    }
    public double getEngine() {
        return engine;
    }
    public String getName() {
        return name;
    }
    public CarModel getCarModel() {
        return carModel;
    }

    //Setters
    public void setBodyStyle(BodyStyles bodyStyle) {
        this.bodyStyle = bodyStyle;
    }
    public void setTransmission(TransmissionTypes transmission) {
        this.transmission = transmission;
    }
    public void setFuelType(FuelTypes fuelType) {
        this.fuelType = fuelType;
    }
    public void setEngine(double engine) {
        this.engine = engine;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCarModel(CarModel carModel) {
        this.carModel = carModel;
    }
    //Constructor

    public CompleteSet() {
    }
    public void generateName(){
        StringJoiner joiner = new StringJoiner("/");
        joiner.add(this.carModel.getModelName());
        joiner.add(this.getBodyStyle().name());
        joiner.add(this.getTransmission().name());
        joiner.add(this.getFuelType().name());
        joiner.add(Double.toString(this.getEngine()));
        this.name = joiner.toString();
    }

    @Override
    public String toString() {
        return "CompleteSet{" +
                "name='" + name + '\'' +
                ", carModel=" + carModel +
                ", bodyStyle=" + bodyStyle +
                ", transmission=" + transmission +
                ", fuelType=" + fuelType +
                ", engine=" + engine +
                ", ID=" + ID +
                '}';
    }
}
