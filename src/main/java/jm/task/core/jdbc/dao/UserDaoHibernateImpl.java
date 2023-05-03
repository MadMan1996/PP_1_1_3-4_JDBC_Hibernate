package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    SessionFactory factory;

    public UserDaoHibernateImpl() {
        this.factory = Util.getSessionFactory();
    }


    @Override
    public void createUsersTable() {
        String SQL = "CREATE TABLE if not exists`users`(" +
                     "`id`BIGINT NOT NULL AUTO_INCREMENT," +
                     "`name`VARCHAR(50) NOT NULL," +
                     "`lastname`VARCHAR(60) NOT NULL," +
                     "`age`TINYINT NOT NULL," +
                     "PRIMARY KEY(`id`))";
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.createSQLQuery(SQL).addEntity(User.class).executeUpdate();
        session.getTransaction().commit();
        session.close();

    }

    @Override
    public void dropUsersTable() {
        String SQL = "DROP TABLE if exists `users`";
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.createSQLQuery(SQL).addEntity(User.class).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        Session session = factory.getCurrentSession();
        session.beginTransaction();
        User user = new User(name, lastName, age);
        session.save(user);
        session.getTransaction().commit();
        session.close();

    }

    @Override
    public void removeUserById(long id) {

        Session session = factory.getCurrentSession();
        session.beginTransaction();
        User curUser = session.get(User.class, id);
        session.delete(curUser);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        Session session = factory.getCurrentSession();
        session.beginTransaction();
        users = session.createQuery("from User")
                .getResultList();
        session.getTransaction().commit();
        session.close();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.createQuery("DELETE User").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}
