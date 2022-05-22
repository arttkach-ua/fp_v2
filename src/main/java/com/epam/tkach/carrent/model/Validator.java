package com.epam.tkach.carrent.model;

import com.epam.tkach.carrent.model.entity.CarBrand;
import com.epam.tkach.carrent.model.entity.CarModel;
import com.epam.tkach.carrent.model.entity.Entity;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.Set;

/**
 * Class used for entities validation
 * @author Tkach A.
 */
public class Validator {
    /**
     * Validates Car Brand Entity
     * @param brand
     * @param errorList - here will be placed errors if brand is not valid
     * @return true if valid, false if not
     */
    public static boolean validateCarBrand(CarBrand brand, ArrayList errorList){
        boolean isValid = true;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        javax.validation.Validator validator = factory.getValidator();

        Set<ConstraintViolation<CarBrand>> violations = validator.validate(brand);

        for (ConstraintViolation<CarBrand> violation : violations) {
            isValid = false;
            errorList.add(violation.getMessage());
        }
        return isValid;
    }
    /**
     * Validates Car Brand Entity
     * @param entity
     * @param errorList - here will be placed errors if brand is not valid
     * @return true if valid, false if not
     */
    public static boolean validateEntity(Entity entity, ArrayList errorList){
        boolean isValid = true;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        javax.validation.Validator validator = factory.getValidator();

        Set<ConstraintViolation<Entity>> violations = validator.validate(entity);

        for (ConstraintViolation<Entity> violation : violations) {
            isValid = false;
            errorList.add(violation.getMessage());
        }
        return isValid;

    }
}
