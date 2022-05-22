package model;

import com.epam.tkach.carrent.model.repository.CarBrandRepoI;
import com.epam.tkach.carrent.model.repository.MySqlImp.CarBrandRepoMySql;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.sql.Connection;

public class CarBrandRepoTest {
    private static CarBrandRepoI repo;

    @BeforeAll
    static void setUp(){
        repo = new CarBrandRepoMySql();
    }

    @Test
    void test1(){

    }
}
