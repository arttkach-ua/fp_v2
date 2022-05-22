package com.epam.tkach.carrent.model.repository;

import com.epam.tkach.carrent.controller.exceptions.CarClassesRepoException;
import com.epam.tkach.carrent.model.entity.enums.CarClass;

import java.util.List;

public interface CarClassesRepoI{
    List<CarClass> getAll() throws CarClassesRepoException;
}
