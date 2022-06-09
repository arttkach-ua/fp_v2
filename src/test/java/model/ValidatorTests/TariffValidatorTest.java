package model.ValidatorTests;

import com.epam.tkach.carrent.model.Validator;
import com.epam.tkach.carrent.model.entity.CarBrand;
import com.epam.tkach.carrent.model.entity.Tariff;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TariffValidatorTest {
    @Test
    public void TariffNullNameTest(){
        ArrayList<String> errors = new ArrayList();
        Tariff tariff = new Tariff();
        boolean result = Validator.validateEntity(tariff,errors);
        assertFalse(result);
    }

    @Test
    public void TariffShortNameTest(){
        ArrayList<String> errors = new ArrayList();
        Tariff tariff = new Tariff();
        tariff.setName("a");
        tariff.setRentPrice(25d);
        tariff.setDriverPrice(25d);
        boolean result = Validator.validateEntity(tariff,errors);
        assertFalse(result);
    }

    @Test
    public void TariffCorrectNameTest(){
        ArrayList<String> errors = new ArrayList();
        Tariff tariff = new Tariff();
        tariff.setName("correct");
        tariff.setRentPrice(25d);
        tariff.setDriverPrice(25d);
        boolean result = Validator.validateEntity(tariff,errors);
        assertTrue(result);
    }

    @Test
    public void TariffZeroRentPriceTest(){
        ArrayList<String> errors = new ArrayList();
        Tariff tariff = new Tariff();
        tariff.setName("correct");
        tariff.setRentPrice(0);
        tariff.setDriverPrice(25d);
        boolean result = Validator.validateEntity(tariff,errors);
        assertFalse(result);
    }
    @Test
    public void TariffZeroDriverPriceTest(){
        ArrayList<String> errors = new ArrayList();
        Tariff tariff = new Tariff();
        tariff.setName("correct");
        tariff.setRentPrice(25d);
        tariff.setDriverPrice(0d);
        boolean result = Validator.validateEntity(tariff,errors);
        assertFalse(result);
    }
}
