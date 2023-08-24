package org.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConnectionUtil {

    private static final String URL = "jdbc:mysql://localhost:3306/SocialMediaApp";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        Connection connection = null; // Ініціалізуйте змінну як null
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection.setAutoCommit(false);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Помилка під час встановлення з'єднання", e);
        }
        return connection;
    }


    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void resetTestDatabase() {
        try (Connection connection = getConnection()) {
            String sql1 = "DELETE FROM messages";
            String sql2 = "DELETE FROM accounts";
            // Додайте інші запити для скидання інших таблиць, якщо потрібно

            try (PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
                 PreparedStatement preparedStatement2 = connection.prepareStatement(sql2)) {

                preparedStatement1.executeUpdate();
                preparedStatement2.executeUpdate();
                // Виконайте інші запити, якщо потрібно

                connection.commit(); // Застосувати зміни, якщо використовується транзакція
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Помилка під час скидання тестової бази даних", e);
        }
    }
}
