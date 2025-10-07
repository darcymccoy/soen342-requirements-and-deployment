package main.system;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseReader {
    static final String SCHEMA_NAME = "train_connections";
    static final String TABLE_NAME = "eu_rail_network";
    private ArrayList<TrainRoute> pullFromDatabase(String query){

        String url = String.format("jdbc:mysql://localhost:3306/%s", SCHEMA_NAME); // Database details
        String username = "root"; // MySQL credentials
        String password = "hugodarcy342";
        //String query = "SELECT * FROM testDatabase.eu_rail_network_ascii WHERE `Departure City` = 'Paris';"; // Query to be run

        ArrayList<TrainRoute> connections = new ArrayList<TrainRoute>();

        try (Connection conn = DriverManager.getConnection(url, username, password);
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

                connections.add(new TrainRoute(routeID, departureCity, arrivalCity, departureTime, arrivalTime, trainType, daysOfOperation, firstClassTicketRate, secondClassTicketRate));
            }

            if(connections.size() == 0){
                // make a no returns thing
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connections;
    }

    public ArrayList<TrainRoute> findArrivals(String arrivalCity){
        String query = String.format("SELECT * FROM %s WHERE `Arrival City` = '%s'",TABLE_NAME, arrivalCity);
        return pullFromDatabase(query);
    }

    public ArrayList<TrainRoute> findDepartures(String departureCity){
        String query = String.format("SELECT * FROM %s WHERE `Departure City` = '%s'",TABLE_NAME, departureCity);
        return pullFromDatabase(query);
    }

    public ArrayList<TrainRoute> findDepartureArrivalPair(String departureCity, String arrivalCity){
        String query = String.format("SELECT * FROM %s WHERE `Departure City` = '%s' AND `Arrival City` = '%s'",TABLE_NAME, departureCity, arrivalCity);
        return pullFromDatabase(query);
    }

    public ArrayList<TrainRoute> listAllCities(){
        String query = String.format("SELECT `Departure City` AS City FROM %s UNION SELECT `Arrival City` AS City FROM %s;",TABLE_NAME, TABLE_NAME);
        return pullFromDatabase(query);
    }
}
