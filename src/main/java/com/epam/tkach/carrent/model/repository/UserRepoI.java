package com.epam.tkach.carrent.model.repository;

import com.epam.tkach.carrent.controller.exceptions.CarBrandRepoException;
import com.epam.tkach.carrent.controller.exceptions.UserRepoException;
import com.epam.tkach.carrent.model.entity.CarBrand;
import com.epam.tkach.carrent.model.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * Interface that contains methods to work with car users id database
 * @author Tkach Artem
 */
public interface UserRepoI {
    /**
     * Method that adds new user to database. If user - role is null default is client
     * @return true if added, false if not
     * @throws UserRepoException
     */
    boolean addNew(User user) throws UserRepoException;
    /**
     * Method that updates user record to database.
     * Search of user must by by ID field, if ID empty
     * @return true if added, false if not
     * @throws UserRepoException
     */
    boolean update(User user) throws UserRepoException;

    /**
     * Method searches existing user by email.
     * @param email
     * @return
     * @throws UserRepoException
     */
    Optional<User> findByEmail(String email) throws UserRepoException;

    /**
     * Method searches existing user by id.
     * @param id
     * @return
     * @throws UserRepoException
     */
    Optional<User> findByID(int id) throws UserRepoException;
    /**
     * Method deletes user from dataBase
     * @param user - statement of User Class
     * @return true if success, false if not deleted
     * @throws UserRepoException
     */
    boolean delete(User user) throws UserRepoException;
    /**
     * Method deletes user from dataBase
     * @param id - id of user in DataBase
     * @return true if success, false if not deleted
     * @throws UserRepoException
     */
    boolean delete(int id) throws UserRepoException;

    /**
     * Method searches users un DB with limit
     * @param currentPage
     * @param recordsPerPage
     * @return
     * @throws UserRepoException
     */
    List<User> getListForPagination(int currentPage, int recordsPerPage) throws UserRepoException;

    List<User> getAll() throws UserRepoException;
}
