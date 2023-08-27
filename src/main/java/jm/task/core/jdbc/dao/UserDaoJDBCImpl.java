package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

//import java.sql.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String sql = "CREATE TABLE `users` (\n `id` BIGINT(20) NOT NULL AUTO_INCREMENT,\n " +
                "`name` VARCHAR(45) NOT NULL,\n `lastName` VARCHAR(45) NOT NULL,\n `age` TINYINT(3) NOT NULL,\n" +
                "  PRIMARY KEY (`id`))";
        try (Connection connection = Util.getConnection()) {
            DatabaseMetaData meta = connection.getMetaData();
            ResultSet resultSet = meta.getTables(null, null, "USERS", new String[]{"TABLE"});
            if (resultSet.next()) {
                System.out.println("Таблица уже существует. Не удалось создать таблицу.");
            } else {
                Statement statement = connection.createStatement();
                statement.executeUpdate(sql);
           //     System.out.println("Таблица создана");
            }
        } catch (SQLException e) {
            System.out.println("Не удалось создать таблицу");
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE users";
        try (Connection connection = Util.getConnection()) {
            DatabaseMetaData meta = connection.getMetaData();
            ResultSet resultSet = meta.getTables(null, null, "USERS", new String[]{"TABLE"});
            if (!resultSet.next()) {
                System.out.println("Таблица не существует. Не удалось удалить таблицу.");
            } else {
                Statement statement = connection.createStatement();
                statement.executeUpdate(sql);
            //    System.out.println("Таблица удалена.");
            }
        } catch (SQLException e) {
            System.out.println("Удалить таблицу не удалось");
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO USERS (ID, NAME, LASTNAME, AGE) VALUES(?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(sql)) {
            preparedStatement.setLong(1, 0);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, lastName);
            preparedStatement.setByte(4, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Не удалось вставить запись");
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id = ";
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate(sql + id);
        } catch (SQLException e) {
            System.out.println("Удалить запись с id=" + id + " не удалось");
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * from users";
        try (Statement statement = Util.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                list.add(user);
            //    System.out.println(user);
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Не удалось прочитать таблицу");
            e.printStackTrace();
        }
        return null;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM users";
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Удалить все записи из таблицы не удалось");
            e.printStackTrace();
        }
    }
}