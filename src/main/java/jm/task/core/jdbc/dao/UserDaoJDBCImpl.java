package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() { // Создание таблицы для User(ов)
        String query = "CREATE TABLE IF NOT EXISTS users " +
                "(id int PRIMARY KEY NOT NULL AUTO_INCREMENT, name VARCHAR(30) NOT NULL" +
                ", lastName VARCHAR (30) NOT NULL, age tinyint NOT NULL)";
        try (PreparedStatement ps = connection.prepareStatement(query)){
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() { // Удаление таблицы User(ов)
        String query = "DROP TABLE IF EXISTS users";
        try (PreparedStatement ps = connection.prepareStatement(query)){
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public void saveUser(String name, String lastName, byte age) { // Добавление User в таблицу
        String query = "INSERT INTO users(name, lastName, age) VALUES(?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public void removeUserById(long id) { // Удаление User из таблицы ( по id )
        String query = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)){
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public List<User> getAllUsers() { // Получение всех User(ов) из таблицы
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";

        try (PreparedStatement ps = connection.prepareStatement(query)){
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
        String query = "TRUNCATE TABLE users";
        try (PreparedStatement ps = connection.prepareStatement(query)){
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
