package main.system;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class DatabaseReader {
    static final String SCHEMA_NAME = "train_connections";
    static final String TABLE_NAME = "eu_rail_network";
    static final String USERNAME = "root";
    static final String URL = String.format("jdbc:mysql://localhost:3306/%s", SCHEMA_NAME);
    static String PASSWORD = "";

    static {
        Properties databaseProperties = new Properties();
        try (FileInputStream fileInput = new FileInputStream("database.properties")) {
            databaseProperties.load(fileInput);
            PASSWORD = databaseProperties.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<TrainRoute> pullObjectFromDatabase(String query) {
        ArrayList<TrainRoute> connections = new ArrayList<TrainRoute>();

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String routeID = rs.getString(1);
                String departureCity = rs.getString(2);
                String arrivalCity = rs.getString(3);
                String departureTime = rs.getString(4);
                String arrivalTime = rs.getString(5);
                String trainType = rs.getString(6);
                String daysOfOperation = rs.getString(7);
                String firstClassTicketRate = rs.getString(8);
                String secondClassTicketRate = rs.getString(9);

                connections.add(new TrainRoute(routeID, departureCity, arrivalCity, departureTime, arrivalTime,
                        trainType, daysOfOperation, firstClassTicketRate, secondClassTicketRate));
            }

            if (connections.size() == 0) {
                // make a no returns thing
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connections;
    }

    private ArrayList<String> pullQueryFromDatabase(String query) {
        ArrayList<String> connections = new ArrayList<String>();

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String city = rs.getString(1);
                connections.add(city);
            }

            if (connections.size() == 0) {
                // make a no returns thing
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connections;
    }

    public ArrayList<TrainRoute> findArrivals(String arrivalCity) {
        String query = String.format("SELECT * FROM %s WHERE `Arrival City` = '%s'", TABLE_NAME, arrivalCity);
        return pullObjectFromDatabase(query);
    }

    public ArrayList<TrainRoute> findDepartures(String departureCity) {
        String query = String.format("SELECT * FROM %s WHERE `Departure City` = '%s'", TABLE_NAME, departureCity);
        return pullObjectFromDatabase(query);
    }

    public ArrayList<TrainRoute> findDepartureArrivalPair(String departureCity, String arrivalCity) {
        String query = String.format("SELECT * FROM %s WHERE `Departure City` = '%s' AND `Arrival City` = '%s'",
                TABLE_NAME, departureCity, arrivalCity);
        return pullObjectFromDatabase(query);
    }

    public ArrayList<String> listAllCities() {
        String query = String.format(
                "SELECT `Departure City` AS City FROM %s UNION SELECT `Arrival City` AS City FROM %s;", TABLE_NAME,
                TABLE_NAME);
        return pullQueryFromDatabase(query);
    }
}
