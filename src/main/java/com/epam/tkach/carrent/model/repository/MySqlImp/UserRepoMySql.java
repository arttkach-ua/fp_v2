package com.epam.tkach.carrent.model.repository.MySqlImp;

import com.epam.tkach.carrent.controller.exceptions.CarBrandRepoException;
import com.epam.tkach.carrent.controller.exceptions.UserRepoException;
import com.epam.tkach.carrent.model.connectionPool.ConnectionPool;
import com.epam.tkach.carrent.model.entity.User;
import com.epam.tkach.carrent.model.entity.enums.Role;
import com.epam.tkach.carrent.model.repository.UserRepoI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepoMySql implements UserRepoI {
    private static final Logger logger = LogManager.getLogger(UserRepoMySql.class);
    private static ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static Connection con = null;

    @Override
    public boolean addNew(User user) throws UserRepoException {
        boolean success = false;
        final String QUERY = "insert into users(phone, password, first_name, second_name, email, document_info, role, blocked,salt,receive_notifications) values (?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            con = connectionPool.getConnection();
            pstmt = con.prepareStatement(QUERY);
            pstmt.setString(1, user.getPhone());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getFirstName());
            pstmt.setString(4, user.getSecondName());
            pstmt.setString(5, user.getEmail());
            pstmt.setString(6,user.getDocumentInfo());
            pstmt.setInt(7,user.getRole().getValue());
            pstmt.setBoolean(8, user.getBlocked());
            pstmt.setBytes(9, user.getSalt());
            pstmt.setBoolean(10, user.isReceiveNotifications());

            pstmt.executeUpdate();
            success=true;
        }catch (SQLException ex) {
            logger.error("Error in addNew method", ex);
            throw new UserRepoException(ex);
        } finally {
            connectionPool.close(con, pstmt,rs);
        }

        return success;
    }

    @Override
    public boolean update(User user) throws UserRepoException {
        boolean success = false;
        final String QUERY = "update users set phone = ?, password = ?, first_name = ?, second_name = ?, email = ?, document_info = ?, role = ?, blocked = ?,salt = ?,receive_notifications = ? where id=?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            con = connectionPool.getConnection();
            pstmt = con.prepareStatement(QUERY);
            pstmt.setString(1, user.getPhone());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getFirstName());
            pstmt.setString(4, user.getSecondName());
            pstmt.setString(5, user.getEmail());
            pstmt.setString(6,user.getDocumentInfo());
            pstmt.setInt(7,user.getRole().getValue());
            pstmt.setBoolean(8, user.getBlocked());
            pstmt.setBytes(9, user.getSalt());
            pstmt.setBoolean(10, user.isReceiveNotifications());
            pstmt.setInt(11,user.getID());

            pstmt.executeUpdate();
            success=true;
        }catch (SQLException ex) {
            logger.error("Error in addNew method", ex);
            throw new UserRepoException(ex);
        } finally {
            connectionPool.close(con, pstmt,null);
        }
        return success;
    }

    @Override
    public Optional<User> findByEmail(String email) throws UserRepoException {
        if (email==null) return Optional.empty();
        if (email.length()==0) return Optional.empty();

        final String QUERY = "select id, email, password, first_name, second_name, document_info, role, blocked, phone, salt, receive_notifications from users where email = (?)";
        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            con = connectionPool.getConnection();
            pstmt = con.prepareStatement(QUERY);
            pstmt.setString(1,email);
            rs = pstmt.executeQuery();
            while (rs.next()){
                user = getUserFromResult(rs);
            }
        } catch (SQLException ex) {
            logger.error("Error in UserRepo.findByEmail method", ex);
            throw new UserRepoException(ex);
        } finally {
            connectionPool.close(con, pstmt,rs);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByID(int id) throws UserRepoException {
        if (id<0) return Optional.empty();

        final String QUERY = "select id, email, password, first_name, second_name, document_info, role, blocked, phone, salt, receive_notifications from users where id = (?)";
        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            con = connectionPool.getConnection();
            pstmt = con.prepareStatement(QUERY);
            pstmt.setInt(1,id);
            rs = pstmt.executeQuery();
            while (rs.next()){
                user = getUserFromResult(rs);
            }
        } catch (SQLException ex) {
            logger.error("Error in UserRepo.findByID method", ex);
            throw new UserRepoException(ex);
        } finally {
            connectionPool.close(con, pstmt,rs);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public boolean delete(User user) throws UserRepoException {
        if (user.getID()==0) return false;
        return delete(user.getID());
    }

    @Override
    public boolean delete(int id) throws UserRepoException {
        if (id==0) return false;
        boolean success =false;
        final String QUERY = "DELETE FROM users WHERE users.id=(?)";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            con = connectionPool.getConnection();
            pstmt = con.prepareStatement(QUERY);
            pstmt.setInt(1,id);
            pstmt.executeUpdate();
            success = true;
        }catch (SQLException ex) {
            logger.error("Error in UserRepo.delete method", ex);
            throw new UserRepoException(ex);
        }finally {
            connectionPool.close(con, pstmt,rs);
        }
        return success;
    }

    @Override
    public List<User> getListForPagination(int currentPage, int recordsPerPage) throws UserRepoException {
        final String QUERY = "select id, email, password, first_name, second_name, document_info, role, blocked, phone, salt, receive_notifications from users limit ?,?";
        List<User> userList= new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            con = connectionPool.getConnection();
            pstmt = con.prepareStatement(QUERY);
            pstmt.setInt(1,(currentPage-1)*recordsPerPage);
            pstmt.setInt(2, recordsPerPage);
            rs = pstmt.executeQuery();
            while (rs.next()){
                userList.add(getUserFromResult(rs));
            }
        } catch (SQLException ex) {
            logger.error("Error in CarRepo:::getListForPagination method", ex);
            throw new UserRepoException(ex);
        } finally {
            connectionPool.close(con, pstmt,rs);
        }
        return userList;
    }

    @Override
    public List<User> getAll() throws UserRepoException {
        final String QUERY = "select id, email, password, first_name, second_name, document_info, role, blocked, phone, salt, receive_notifications from users";
        List<User> userList= new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;

        try{
            con = connectionPool.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(QUERY);
            while (rs.next()){
                userList.add(getUserFromResult(rs));
            }
        } catch (SQLException ex) {
            logger.error("Error in CarRepo:::getAll method", ex);
            throw new UserRepoException(ex);
        } finally {
            connectionPool.close(con, stmt,rs);
        }
        return userList;
    }

    @Override
    public int getCountInDb() throws UserRepoException {
        final String QUERY = "select count(id) from users";
        int countInDb = 0;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            con = connectionPool.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(QUERY);
            while (rs.next()){
                countInDb = rs.getInt(1);
            }
        } catch (SQLException ex) {
            logger.error("Error in UserRepo.getCountInDb method", ex);
            throw new UserRepoException(ex);
        } finally {
            connectionPool.close(con, stmt,rs);
        }
        return countInDb;
    }

    /**
     * Creates instance of User class from result set
     * @param rs
     * @return
     */
    private User getUserFromResult(ResultSet rs){
        User user = new User();
        try {
            user.setID(rs.getInt("id"));
            user.setEmail(rs.getString("email"));
            user.setFirstName(rs.getString("first_name"));
            user.setSecondName(rs.getString("second_name"));
            user.setPhone(rs.getString("phone"));
            user.setDocumentInfo(rs.getString("document_info"));
            user.setRole(Role.getByID(rs.getInt("role")));
            user.setBlocked(rs.getBoolean("blocked"));
            user.setPassword(rs.getString("password"));
            user.setSalt(rs.getBytes("salt"));
            user.setReceiveNotifications(rs.getBoolean("receive_notifications"));

        } catch (SQLException ex) {
            logger.error(ex);
        }

        return user;
    }


}
