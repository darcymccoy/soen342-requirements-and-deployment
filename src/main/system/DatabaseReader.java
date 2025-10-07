package main.system;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseReader {
    public ArrayList<TrainRoutes> PullDataFromDatabase(){


        String url = "jdbc:mysql://localhost:3306/testDatabase"; // Database details
        String username = "root"; // MySQL credentials
        String password = "Facile123";
        String query = "SELECT * FROM testDatabase.eu_rail_network_ascii WHERE `Departure City` = 'Paris';"; // Query to be run

        ArrayList<TrainRoutes> connections = new ArrayList<TrainRoutes>();

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

                connections.add(new TrainRoutes(routeID, departureCity, arrivalCity, departureTime, arrivalTime, trainType, daysOfOperation, firstClassTicketRate, secondClassTicketRate));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connections;

    }
}
