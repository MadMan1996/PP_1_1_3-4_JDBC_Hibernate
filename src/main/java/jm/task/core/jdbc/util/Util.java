package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.bytecode.enhance.internal.bytebuddy.EnhancerImpl;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public final class Util {
    // реализуйте настройку соеденения с БД
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/world";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "NhbyblfnjNj,fuj34!";

    private static final String DB_HIBER_DIALECT = "org.hibernate.dialect.MySQL5Dialect";

    private static Connection connection;

    private static SessionFactory sessionFactory;

    static {
        loadClass();
    }

    private Util() {

    }

    private static void loadClass() {
        try {
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

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            Properties settings = new Properties();
            settings.put(Environment.DRIVER, DB_DRIVER);
            settings.put(Environment.URL, DB_URL);
            settings.put(Environment.USER, DB_USERNAME);
            settings.put(Environment.PASS, DB_PASSWORD);
            settings.put(Environment.DIALECT, DB_HIBER_DIALECT);

            settings.put(Environment.SHOW_SQL, "true");
            settings.put(Environment.HBM2DDL_AUTO, "validate");
            settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

            configuration.setProperties(settings);

            configuration.addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        return sessionFactory;
    }



}
