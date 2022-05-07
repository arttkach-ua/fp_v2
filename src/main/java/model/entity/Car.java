package model.entity;

import model.entity.enums.CarClasses;
import model.entity.enums.FuelTypes;

public class Car extends Entity{
    CarBrand brand;
    CarModel model;
    CarClasses carClass;
    int graduationYear;
    String bodyStyle;//!!!!!
    String transmission;
    FuelTypes fuelType;
}
