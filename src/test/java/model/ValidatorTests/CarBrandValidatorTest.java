package model.ValidatorTests;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import com.epam.tkach.carrent.model.Validator;
import com.epam.tkach.carrent.model.entity.CarBrand;

import java.util.ArrayList;

public class CarBrandValidatorTest {
    @Test
    public void CarBrandNullNameTest(){
        ArrayList<String> errors = new ArrayList();
        CarBrand brand= new CarBrand();
        boolean result = Validator.validateCarBrand(brand,errors);
        assertFalse(result);
    }

    @Test
    public void CarBrandShortNameTest(){
        ArrayList<String> errors = new ArrayList();
        CarBrand brand= new CarBrand();
        brand.setCarBrandName("a");
        boolean result = Validator.validateCarBrand(brand,errors);
        assertFalse(result);
    }

    @Test
    public void CarBrandCorrectNameTest(){
        ArrayList<String> errors = new ArrayList();
        CarBrand brand= new CarBrand();
        brand.setCarBrandName("Audi");
        boolean result = Validator.validateCarBrand(brand,errors);
        assertTrue(result);
    }
}
