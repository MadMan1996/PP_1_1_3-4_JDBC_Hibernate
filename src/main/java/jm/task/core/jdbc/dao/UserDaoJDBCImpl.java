package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement();) {
            String SQL = "CREATE TABLE if not exists`users`(" +
                    "`id`BIGINT NOT NULL AUTO_INCREMENT," +
                    "`name`VARCHAR(50) NOT NULL," +
                    "`lastname`VARCHAR(60) NOT NULL," +
                    "`age`TINYINT NOT NULL," +
                    "PRIMARY KEY(`id`))";
            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement();) {
            String SQL = "DROP TABLE if exists`users`";
            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String SQL = "INSERT INTO `users` (name, lastName, age) VALUES (?,?,?)";
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL);) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String SQL = "DELETE FROM `users` WHERE id = ?";
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL);) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users");) {

            ResultSet allUsers = preparedStatement.executeQuery();
            while(allUsers.next()){
                result.add(new User(
                        allUsers.getString(2),
                        allUsers.getString(3),
                        allUsers.getByte(4)
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement();) {
            statement.executeUpdate("DELETE FROM `users`");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
