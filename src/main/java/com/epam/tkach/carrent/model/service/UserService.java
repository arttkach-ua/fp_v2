package com.epam.tkach.carrent.model.service;

import com.epam.tkach.carrent.controller.exceptions.UserRepoException;
import com.epam.tkach.carrent.model.entity.User;
import com.epam.tkach.carrent.model.repository.RepositoryFactory;
import com.epam.tkach.carrent.model.repository.UserRepoI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class UserService {
    private static final Logger logger = LogManager.getLogger(UserService.class);
    public static User findByID(int id) throws UserRepoException {
        if (id<1) return null;
        UserRepoI repo = RepositoryFactory.getUserRepo();
        Optional<User> opt = repo.findByID(id);
        if (opt.isEmpty()) throw new UserRepoException();
        return opt.get();
    }
    public static User findByEmail(String email) throws UserRepoException{
        UserRepoI repo = RepositoryFactory.getUserRepo();
        Optional<User> opt = repo.findByEmail(email);
        if (opt.isEmpty()) throw new UserRepoException();
        return opt.get();
    }
    public static boolean userIsPresentInDB(String email) throws UserRepoException{
        UserRepoI repo = RepositoryFactory.getUserRepo();
        Optional<User> opt = repo.findByEmail(email);
        return opt.isPresent();
    }
    public static void update(User user)throws UserRepoException{
        UserRepoI repo = RepositoryFactory.getUserRepo();
        repo.update(user);
    }

    public static void setBlockToUser(int id, boolean newValue) throws UserRepoException{
        UserRepoI repo = RepositoryFactory.getUserRepo();
        User user = UserService.findByID(id);
        user.setBlocked(newValue);
        logger.debug("new Value:::".concat(Boolean.toString(newValue)));
        repo.update(user);
    }
}
