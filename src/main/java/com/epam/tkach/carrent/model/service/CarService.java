package com.epam.tkach.carrent.model.service;

import com.epam.tkach.carrent.controller.exceptions.CarRepoException;
import com.epam.tkach.carrent.controller.exceptions.OrderRepoException;
import com.epam.tkach.carrent.model.entity.Car;
import com.epam.tkach.carrent.model.entity.Order;
import com.epam.tkach.carrent.model.repository.CarRepoI;
import com.epam.tkach.carrent.model.repository.OrderRepoI;
import com.epam.tkach.carrent.model.repository.RepositoryFactory;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class CarService {

    public static void update(Car car, Connection con) throws CarRepoException {
        CarRepoI repo = RepositoryFactory.getCarRepo();
        repo.update(car, con);
    }

    public static Car getById(int ID) throws CarRepoException{
        CarRepoI repo = RepositoryFactory.getCarRepo();
        Optional<Car> carOpt = repo.getById(ID);
        if (carOpt.isEmpty()) throw new CarRepoException();
        return carOpt.get();
    }

    public static int getCountInDB(LinkedHashMap<String, Object> filters) throws CarRepoException {
        CarRepoI repo = RepositoryFactory.getCarRepo();
        return repo.getCountInDB(filters);
    }

    public static List<Car> getList(int currentPage, int recordsPerPage, LinkedHashMap<String, Object> filters, String orderBy) throws CarRepoException {
        CarRepoI repo = RepositoryFactory.getCarRepo();
        return repo.getListForPagination(currentPage, recordsPerPage,filters, orderBy);
    }

    public static List<String> getSortingList(){
        List<String> sortList = new ArrayList<>();
        sortList.add("price.down");
        sortList.add("price.up");
        sortList.add("brand.up");
        sortList.add("brand.down");
        return sortList;
    }

    /**
     * Function return column name for SQL query
     * @param sortName - name of sorting param. All list in getSortingList
     * @return
     */
    public static String getColumnNameToSort(String sortName){
        if (sortName==null) return "";
        switch (sortName){
            case "price.down": return "t.rent_price desc";
            case "price.up": return "t.rent_price ASC";
            case "brand.up": return "cb.brand ASC";
            case "brand.down": return "cb.brand desc";
            default: return "";
        }
    }

    public static boolean addNew(Car car) throws CarRepoException {
        CarRepoI carRepo = RepositoryFactory.getCarRepo();
        return carRepo.addNew(car);
    }
}
