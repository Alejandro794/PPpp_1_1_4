package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() { // Создание таблицы для User(ов)
        try (Connection connection = Util.getConnection();
             PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS users " +
                     "(id int PRIMARY KEY NOT NULL AUTO_INCREMENT" +
                     ", name VARCHAR(30) NOT NULL" +
                     ", lastName VARCHAR (30) NOT NULL" +
                     ", age tinyint NOT NULL)")) {
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() { // Удаление таблицы User(ов)
        try (Connection connection = Util.getConnection();
             PreparedStatement ps = connection.prepareStatement("DROP TABLE if EXISTS users")) {
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public void saveUser(String name, String lastName, byte age) { // Добавление User в таблицу
        try (Connection connection = Util.getConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO users(name, lastName, age) VALUES(?, ?, ?)")) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public void removeUserById(long id) { // Удаление User из таблицы ( по id )
        try (Connection connection = Util.getConnection();
             PreparedStatement ps = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public List<User> getAllUsers() { // Получение всех User(ов) из таблицы
        List<User> users = new ArrayList<>();

        try (Connection connection = Util.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM users")
        ) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String lastName = rs.getString("lastName");
                byte age = rs.getByte("age");

                users.add(new User(name, lastName, age));
            }

        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return users;
    }

    public void cleanUsersTable() { // Очистка содержания таблицы
        try (Connection connection = Util.getConnection();
             PreparedStatement ps = connection.prepareStatement("TRUNCATE TABLE users")) {
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
