package main.system;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class DatabaseReader {
    static final String SCHEMA_NAME = "train_connections";
    static final String TABLE_NAME = "eu_rail_network";
    static final String USERNAME = "root";
    static final String URL = String.format("jdbc:mysql://localhost:3306/%s", SCHEMA_NAME);
    static String PASSWORD = "";
    static String[] filters = {null, null,null,null,null,null};
    static String[] values = {null, null,null,null,null,null};


    static {
        Properties databaseProperties = new Properties();
        try (FileInputStream fileInput = new FileInputStream("database.properties")) {
            databaseProperties.load(fileInput);
            PASSWORD = databaseProperties.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Object reader(String query){
        StringBuilder queryBuilder = new StringBuilder(query);
        for (String x : filters){
            if(x != null){
                queryBuilder.append(" AND ").append(x);
            }
        }
        query = queryBuilder.toString();
        System.out.println(query);
        try {
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement stmt;
            stmt = conn.prepareStatement(query);
            int counter = 1;
            for(String x : values){
                if(x != null){
                    stmt.setString(counter++,x);
                }
            }
            counter = 1;

            ResultSet rs = stmt.executeQuery();

            ArrayList<TrainRoute> connections = new ArrayList<TrainRoute>();

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
            return connections;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
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

    //TODO fix all SQL statements to have prepared statement
    public ArrayList<TrainRoute> findArrivals(String arrivalCity) {
        String query = String.format("SELECT * FROM %s WHERE `Arrival City` = '%s'", TABLE_NAME, arrivalCity);
        return (ArrayList<TrainRoute>)reader(query);
    }

    public ArrayList<TrainRoute> findDepartures(String departureCity) {
        String query = String.format("SELECT * FROM %s WHERE `Departure City` = '%s'", TABLE_NAME, departureCity);
        return (ArrayList<TrainRoute>)reader(query);
    }

    public ArrayList<TrainRoute> findDepartureArrivalPair(String departureCity, String arrivalCity) {
        String query = String.format("SELECT * FROM %s WHERE `Departure City` = '%s' AND `Arrival City` = '%s'",
                TABLE_NAME, departureCity, arrivalCity);
        return (ArrayList<TrainRoute>)reader(query);
    }

    public ArrayList<String> listAllCities() {
        String query = String.format(
                "SELECT `Departure City` AS City FROM %s UNION SELECT `Arrival City` AS City FROM %s;", TABLE_NAME,
                TABLE_NAME);
        return pullQueryFromDatabase(query);
    }

    public void filterByDepTime(String val){
        filters[0] = "`Departure Time` = ?";
        values[0] = val;
    }

    public void filterByArrTime(String val){
        filters[1] = "`Arrival Time` = ?";
        values[1] = val;
    }

    public void filterByTrainType(String val){
        filters[2] = "`Train Type` = ?";
        values[2] = val;
    }

    public void filterByDOO(String val){
        filters[3] = "`Days of Operation` = ?";
        values[3] = val;
    }

    public void filterByFirstClass(String val){
        filters[4] = "`First Class ticket rate (in euro)` = ?";
        values[4] = val;
    }

    public void filterBySecondClass(String val){
        filters[5] = "`Second Class ticket rate (in euro)` = ?";
        values[5] = val;
    }

    public void resetArrs() {
        Arrays.fill(filters, null);
        Arrays.fill(values, null);
    }
}
