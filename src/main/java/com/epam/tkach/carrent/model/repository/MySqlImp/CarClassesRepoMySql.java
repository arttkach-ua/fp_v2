package com.epam.tkach.carrent.model.repository.MySqlImp;

import com.epam.tkach.carrent.controller.exceptions.CarClassesRepoException;
import com.epam.tkach.carrent.model.entity.enums.CarClass;
import com.epam.tkach.carrent.model.repository.CarClassesRepoI;

import java.util.ArrayList;
import java.util.List;

public class CarClassesRepoMySql implements CarClassesRepoI {
    @Override
    public List<CarClass> getAll() throws CarClassesRepoException {
        List<CarClass> list = new ArrayList();
        for (CarClass carClass: CarClass.values()){
            list.add(carClass);
        }
        return list;
    }
}
