package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Util {
    // реализуйте настройку соеденения с БД
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/world";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "NhbyblfnjNj,fuj34!";

    private static Connection connection;

    static {
        loadClass();
    }
    private Util() {

    }

    private static void loadClass() {
        try{
            Class.forName(DB_DRIVER);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
       return connection;
    }

}
