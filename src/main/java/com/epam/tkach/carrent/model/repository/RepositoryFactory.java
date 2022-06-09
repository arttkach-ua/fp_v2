package com.epam.tkach.carrent.model.repository;

import com.epam.tkach.carrent.controller.exceptions.*;
import com.epam.tkach.carrent.model.repository.MySqlImp.*;

/**
 * Class is user to get current instances of repositories
 */
public class RepositoryFactory {
    public static final CarBrandRepoI getCarBrandRepo() throws CarBrandRepoException{
        if (DataBaseSettings.getCurrentDBSelector().equals(DataBaseSettings.DataBaseSelector.MY_SQL)){
            return new CarBrandRepoMySql();
        }else{
            throw new CarBrandRepoException("No realisation for selected db");
        }
    }

    public static final CarModelRepoI getCarModelRepo() throws CarModelRepoException {
        if (DataBaseSettings.getCurrentDBSelector().equals(DataBaseSettings.DataBaseSelector.MY_SQL)){
            return new CarModelRepoMySql();
        }else{
            throw new CarModelRepoException("No realisation for selected db");
        }
    }

    public static final CarRepoI getCarRepo() throws CarRepoException {
        if (DataBaseSettings.getCurrentDBSelector().equals(DataBaseSettings.DataBaseSelector.MY_SQL)){
            return new CarRepoMySql();
        }else{
            throw new CarRepoException("No realisation for selected db");
        }
    }

    public static final UserRepoI getUserRepo() throws UserRepoException {
        if (DataBaseSettings.getCurrentDBSelector().equals(DataBaseSettings.DataBaseSelector.MY_SQL)){
            return new UserRepoMySql();
        }else{
            throw new UserRepoException("No realisation for selected db");
        }
    }

    public static final OrderRepoI getOrderRepo() throws OrderRepoException {
        if (DataBaseSettings.getCurrentDBSelector().equals(DataBaseSettings.DataBaseSelector.MY_SQL)){
            return new OrderRepoMySql();
        }else{
            throw new OrderRepoException("No realisation for selected db");
        }
    }

    public static final TariffRepoI getTariffRepo() throws TariffException {
        if (DataBaseSettings.getCurrentDBSelector().equals(DataBaseSettings.DataBaseSelector.MY_SQL)){
            return new TariffRepoMySql();
        }else{
            throw new TariffException("No realisation for selected db");
        }
    }

    public static final CompleteSetsRepoI getCompleteSetsRepo() throws CompleteSetsRepoException {
        if (DataBaseSettings.getCurrentDBSelector().equals(DataBaseSettings.DataBaseSelector.MY_SQL)){
            return new CompleteSetsRepoMySql();
        }else{
            throw new CompleteSetsRepoException("No realisation for selected db");
        }
    }
}
