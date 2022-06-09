package com.epam.tkach.carrent.model.service;

import com.epam.tkach.carrent.controller.exceptions.UserRepoException;
import com.epam.tkach.carrent.model.entity.User;
import com.epam.tkach.carrent.model.repository.RepositoryFactory;
import com.epam.tkach.carrent.model.repository.UserRepoI;

import java.util.Optional;

public class UserService {
    public static User findByID(int id) throws UserRepoException {
        if (id<1) return null;
        UserRepoI repo = RepositoryFactory.getUserRepo();
        Optional<User> opt = repo.findByID(id);
        if (opt.isEmpty()) throw new UserRepoException();
        return opt.get();
    }
}
