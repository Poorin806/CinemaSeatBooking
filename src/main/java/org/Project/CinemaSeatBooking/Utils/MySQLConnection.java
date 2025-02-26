package org.Project.CinemaSeatBooking.Utils;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;
import java.util.UUID;

public class MySQLConnection {

    private static final Dotenv dotnev = Dotenv.load();

    private static final String DB_URL = dotnev.get("DB_URL");
    private static final String DB_USERNAME = dotnev.get("DB_USER");
    private static final String DB_PASSWORD = dotnev.get("DB_PASSWORD");

    private static Connection connection = null;
    private static Statement statement = null;

    public static void getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                System.out.println("Database connection established.");
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException("Database connection failed!", e);
            }
        }
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                statement.close();
                connection.close();
                connection = null;
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                throw new RuntimeException("Error closing database connection", e);
            }
        }
    }

    public static ResultSet fetchData(String sql) {
        if (connection == null) {
            throw new IllegalStateException("Database connection has not been established. Call getConnection() first.");
        }

        try {
            statement = connection.createStatement();
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching data", e);
        }

    }

    public static int query(String sql) {
        if (connection == null) {
            throw new IllegalStateException("Database connection has not been established. Call getConnection() first.");
        }

        try {
            statement = connection.createStatement();
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Error executing query", e);
        }
    }

    public static String genreratePK() {
        return UUID.randomUUID().toString();
    }


}