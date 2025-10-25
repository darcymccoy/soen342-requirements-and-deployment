package main.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {
    private static final String URL = "jdbc:sqlite:database/travel_booking.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}