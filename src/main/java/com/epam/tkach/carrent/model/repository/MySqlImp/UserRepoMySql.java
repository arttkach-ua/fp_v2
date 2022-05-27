package com.epam.tkach.carrent.model.repository.MySqlImp;

import com.epam.tkach.carrent.controller.exceptions.CarBrandRepoException;
import com.epam.tkach.carrent.controller.exceptions.UserRepoException;
import com.epam.tkach.carrent.model.connectionPool.ConnectionPool;
import com.epam.tkach.carrent.model.entity.CarBrand;
import com.epam.tkach.carrent.model.entity.User;
import com.epam.tkach.carrent.model.entity.enums.Role;
import com.epam.tkach.carrent.model.repository.UserRepoI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserRepoMySql implements UserRepoI {
    private static final Logger logger = LogManager.getLogger(UserRepoMySql.class);
    private static ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static Connection con = null;

    @Override
    public boolean addNew(User user) throws UserRepoException {
        boolean success = false;
        final String QUERY = "insert into users(phone, password, first_name, second_name, email, document_info, role, blocked,salt) values (?,?,?,?,?,?,?,?,?)";

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
        return false;
    }

    @Override
    public Optional<User> findByEmail(String email) throws UserRepoException {
        if (email==null) return Optional.empty();
        if (email.length()==0) return Optional.empty();

        final String QUERY = "select id, email, password, first_name, second_name, document_info, role, blocked, phone, salt from users where email = (?)";
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

        final String QUERY = "select id, email, password, first_name, second_name, document_info, role, blocked, phone, salt from users where id = (?)";
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

        } catch (SQLException ex) {
            logger.error(ex);
        }

        return user;
    }
}
