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
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
//            System.out.println("Таблица создана");
        } finally {
            sessionFactory.close();
       }
    }

    @Override
    public void dropUsersTable() {

        String sql = "DROP TABLE IF EXISTS user";
        SessionFactory sessionFactory = Util.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
//            System.out.println("Таблица удалена");
        } finally {
            sessionFactory.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        SessionFactory sessionFactory = Util.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        User user = new User(name, lastName, age);
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
//            System.out.println("Пользователь добавлен");
        } finally {
            sessionFactory.close();
        }
    }

    @Override
    public void removeUserById(long id) {

        SessionFactory sessionFactory = Util.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        } finally {
            sessionFactory.close();
        }
    }

    @Override
    public List<User> getAllUsers() {

        SessionFactory sessionFactory = Util.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            List<User> users = session.createQuery("FROM User").getResultList();
            session.getTransaction().commit();
            return users;
        } finally {
            sessionFactory.close();
        }
}

    @Override
    public void cleanUsersTable() {

        SessionFactory sessionFactory = Util.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            session.getTransaction().commit();
        } finally {
            sessionFactory.close();
        }
    }
}

