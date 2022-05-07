package model.connectionPool;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;

public class ConnectionPool {
    private static ConnectionPool instance = null;
    private static final Logger logger = Logger.getLogger(ConnectionPool.class);

    private ConnectionPool() { }

    public static synchronized ConnectionPool getInstance(){
        if (instance != null) {
            return instance;
        }
        synchronized (ConnectionPool.class) {
            if (instance == null) {
                instance = new ConnectionPool();
            }
        }
        return instance;
    }

    public Connection getConnection(){
        Context ctx = null;
        DataSource dataSource = null;
        Connection con = null;
        try{
            ctx = new InitialContext();
            dataSource = (DataSource)ctx.lookup("java:comp/env/jdbc/car_rent");
            con = dataSource.getConnection();
            logger.info("Connection to pool success");

        } catch (SQLException | NamingException ex) {
            logger.error("Pool connection error", ex);
        }
        return con;
    }

    /**
     * closes connection, statement, resultSet
     * @param rs ResultSet
     */
    public void close(Connection con, Statement stmt, ResultSet rs){
        close(con);
        close(stmt);
        close(rs);
    }
    /**
     * closes ResultSet
     * @param rs ResultSet
     */
    public void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * closes Connection
     * @param con Connection
     */
    public void close(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * closes Statament
     * @param st Statement
     */
    public void close(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
