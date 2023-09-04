package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {

        String sql = "CREATE TABLE IF NOT EXISTS user " +
                "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                "age TINYINT NOT NULL)";

        SessionFactory sessionFactory = Util.getSessionFactory();
        try (Session session = sessionFactory.getCurrentSession())
        {
            session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void dropUsersTable() {

        String sql = "DROP TABLE IF EXISTS user";
        SessionFactory sessionFactory = Util.getSessionFactory();
        try (Session session = sessionFactory.getCurrentSession())
        {
            session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();

        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        SessionFactory sessionFactory = Util.getSessionFactory();
        try (Session session = sessionFactory.getCurrentSession())
        {
            User user = new User(name, lastName, age);
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public void removeUserById(long id) {

        SessionFactory sessionFactory = Util.getSessionFactory();
        try (Session session = sessionFactory.getCurrentSession())
        {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<User> getAllUsers() {

        SessionFactory sessionFactory = Util.getSessionFactory();
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            List<User> users = session.createQuery("FROM User").getResultList();
            session.getTransaction().commit();
            return users;
        }
    }

    @Override
    public void cleanUsersTable() {

        SessionFactory sessionFactory = Util.getSessionFactory();
        try (Session session = sessionFactory.getCurrentSession())
        {
            session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            session.getTransaction().commit();
        }
    }
}

