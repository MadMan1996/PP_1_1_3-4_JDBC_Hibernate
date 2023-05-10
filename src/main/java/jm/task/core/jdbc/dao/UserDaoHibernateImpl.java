package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class UserDaoHibernateImpl implements UserDao {
    SessionFactory factory;

    public UserDaoHibernateImpl() {
        this.factory = Util.getSessionFactory();
    }


    @Override
    public void createUsersTable() {
        String SQL = "CREATE TABLE if not exists`users2`(" +
                "`id`BIGINT NOT NULL AUTO_INCREMENT," +
                "`name`VARCHAR(50) NOT NULL," +
                "`lastname`VARCHAR(60) NOT NULL," +
                "`age`TINYINT NOT NULL," +
                "PRIMARY KEY(`id`))";
        Transaction transaction = null;
        try (Session session = factory.openSession();) {
            transaction = session.beginTransaction();
            session.createSQLQuery(SQL).addEntity(User.class).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }


    }

    @Override
    public void dropUsersTable() {
        String SQL = "DROP TABLE if exists `users2`";

        Transaction transaction = null;
        try (Session session = factory.openSession();) {
            transaction = session.beginTransaction();
            session.createSQLQuery(SQL).addEntity(User.class).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        Transaction transaction = null;

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                System.out.println(transaction.getStatus());
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }

    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = factory.openSession();) {
            transaction = session.beginTransaction();
            User curUser = session.get(User.class, id);
            session.delete(curUser);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null&&transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<User> getAllUsers() {


        List<User> users = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = factory.openSession();) {
            transaction = session.beginTransaction();
            users = session.createQuery("from User")
                    .getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }

        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = factory.openSession();) {
            transaction = session.beginTransaction();
            session.createQuery("DELETE User").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null&&transaction.isActive()) {
                e.printStackTrace();
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }
    }
}
