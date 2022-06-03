package com.epam.tkach.carrent.model.repository;

import com.epam.tkach.carrent.controller.exceptions.OrderRepoException;
import com.epam.tkach.carrent.model.entity.Order;

public interface OrderRepoI {
    boolean addNew(Order order) throws OrderRepoException;

    boolean update(Order order) throws OrderRepoException;
}
