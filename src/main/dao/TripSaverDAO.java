package main.dao;

import main.model.*;
import main.util.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TripSaverDAO {
    // Database table names
    private static final String CLIENT_RESERVATIONS = "client_reservations";
    private static final String CLIENTS = "clients";
    private static final String CONNECTIONS = "connections";
    private static final String RESERVATIONS = "reservations";
    private static final String TRIP_CONNECTIONS = "trip_connections";
    private static final String TRIP_RESERVATIONS = "trip_reservations";
    private static final String TRIPS = "trips";



    public static void parseTripInformation(Trip trip){
        System.out.println("Saving to database... this will only take a moment! :)");
        UUID trip_id = trip.getTripId();
        TrainConnection connection = trip.getTrainConnection();
        List<Reservation> reservationList = trip.getReservations();

        addTrip(trip_id);
        saveTripConnectionRel(trip_id, connection);
        for(Reservation r : reservationList){
            addReservation(r);
            addClient(r.getClient());
            saveTripReservationRel(trip_id, r);
            saveClientReservationRel(r);
        }
        System.out.println("Save complete!");

    }

    // Trip saving method
    public static void addTrip(UUID trip_id){
        String sql = String.format("""
                INSERT INTO %s VALUES(?);""",TRIPS);
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)){

            statement.setString(1, trip_id.toString());

            statement.executeUpdate();

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Trip-Connection relation saving method
    public static void saveTripConnectionRel(UUID trip_id, TrainConnection connection){
        String sql = String.format("""
                INSERT INTO %s VALUES(?,?,?,?);""", TRIP_CONNECTIONS);
        String route1_id = connection.getRoute1().getRouteID();
        String route2_id;
        if(connection.getRoute2() != null){
            route2_id = connection.getRoute2().getRouteID();
        }else{
            route2_id = null;
        }
        String route3_id;
        if(connection.getRoute3() != null){
            route3_id = connection.getRoute3().getRouteID();
        } else {
            route3_id = null;
        }

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)){
            statement.setString(1, trip_id.toString());
            statement.setString(2, route1_id);
            statement.setString(3, route2_id);
            statement.setString(4, route3_id);

            statement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Reservation saving method
    public static void addReservation(Reservation reservation){
        String sql = String.format("""
                INSERT INTO %s VALUES(?);""", RESERVATIONS);

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)){
            statement.setLong(1, reservation.getTicket().getId());
            statement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Trip-Reservation relation saving method
    public static void saveTripReservationRel(UUID trip_id, Reservation reservation){
        String sql = String.format("""
                INSERT INTO %s VALUES(?,?);""",TRIP_RESERVATIONS);
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)){
            statement.setString(1, trip_id.toString());
            statement.setLong(2, reservation.getTicket().getId());
            statement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Client-Reservation relation saving method
    public static void saveClientReservationRel(Reservation reservation){
        String sql = String.format("""
                INSERT INTO %s VALUES(?,?);""",CLIENT_RESERVATIONS);
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)){
            statement.setString(1, reservation.getClient().getPassengerId().toString());
            statement.setLong(2, reservation.getTicket().getId());
            statement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Client saving methods
    public static void addClient(Client c){
        if(!clientExists(c.getPassengerId())){
            String client_id = c.getPassengerId();
            String first_name = c.getFirstname();
            String last_name = c.getLastname();
            int age = c.getAge();

            String sql = String.format("""
                    INSERT INTO %s 
                    VALUES(?,?,?,?);""", CLIENTS);

            try (Connection conn = DBHelper.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)){

                statement.setString(1,client_id);
                statement.setString(2, first_name);
                statement.setString(3, last_name);
                statement.setInt(4, age);

                statement.executeUpdate();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }
    public static boolean clientExists(String client_id){
        String sql = String.format("""
                SELECT `client_id` FROM %s WHERE `client_id` = ?;""", CLIENTS);
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)){

            statement.setString(1, client_id);

            ResultSet rs = statement.executeQuery();
            if(!rs.next()){

                return false;
            }

            return true;

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }




}
