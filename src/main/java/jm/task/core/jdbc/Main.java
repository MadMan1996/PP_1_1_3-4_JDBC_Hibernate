package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

public class Main {
    public static void main(String[] args) throws Exception {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Andrey", "Petrov", (byte) 45);
        userService.saveUser("Artem", "Ivanov", (byte) 4);
        userService.saveUser("Inokentiy", "Sidorov", (byte) 70);
        userService.saveUser("Zaur", "Tregulov", (byte) 27);
        for(User u : userService.getAllUsers()){
            System.out.println(u);
        };
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
