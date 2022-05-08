package model.entity;

import model.entity.enums.CarClasses;
import model.entity.enums.FuelTypes;
import model.entity.enums.TransmissionTypes;

public class Car extends Entity{
    CarBrand brand;
    CarModel model;
    CarClasses carClass;
    int graduationYear;
    String bodyStyle;//!!!!!
    TransmissionTypes transmission;
    FuelTypes fuelType;
}
