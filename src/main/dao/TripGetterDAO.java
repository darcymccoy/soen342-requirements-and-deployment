package main.dao;

import main.model.Reservation;
import main.model.TrainConnection;
import main.model.TrainRoute;
import main.model.Trip;
import main.util.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TripGetterDAO {
    private static final String CLIENT_RESERVATIONS = "client_reservations";
    private static final String CLIENTS = "clients";
    private static final String CONNECTIONS = "connections";
    private static final String RESERVATIONS = "reservations";
    private static final String TRIP_CONNECTIONS = "trip_connections";
    private static final String TRIP_RESERVATIONS = "trip_reservations";
    private static final String TRIPS = "trips";

    public static List<Trip> getTrip(String last_name, String client_id) {
        List<Trip> trips = new ArrayList<Trip>();
        String sql = """
    SELECT
        t.trip_id,
        tr.ticket_id,
        cr.client_id,
        c.first_name,
        c.last_name,
        c.age,
    
        -- Route 1
        conn1.route_id AS route1_id,
        conn1.departure_city AS route1_departure_city,
        conn1.arrival_city AS route1_arrival_city,
        conn1.departure_time AS route1_departure_time,
        conn1.arrival_time AS route1_arrival_time,
        conn1.train_type AS route1_train_type,
        conn1.days_of_operation AS route1_days_of_operation,
        conn1.first_class_ticket_rate AS route1_first_class_ticket_rate,
        conn1.second_class_ticket_rate AS route1_second_class_ticket_rate,
    
        -- Route 2
        conn2.route_id AS route2_id,
        conn2.departure_city AS route2_departure_city,
        conn2.arrival_city AS route2_arrival_city,
        conn2.departure_time AS route2_departure_time,
        conn2.arrival_time AS route2_arrival_time,
        conn2.train_type AS route2_train_type,
        conn2.days_of_operation AS route2_days_of_operation,
        conn2.first_class_ticket_rate AS route2_first_class_ticket_rate,
        conn2.second_class_ticket_rate AS route2_second_class_ticket_rate,
    
        -- Route 3
        conn3.route_id AS route3_id,
        conn3.departure_city AS route3_departure_city,
        conn3.arrival_city AS route3_arrival_city,
        conn3.departure_time AS route3_departure_time,
        conn3.arrival_time AS route3_arrival_time,
        conn3.train_type AS route3_train_type,
        conn3.days_of_operation AS route3_days_of_operation,
        conn3.first_class_ticket_rate AS route3_first_class_ticket_rate,
        conn3.second_class_ticket_rate AS route3_second_class_ticket_rate
    
    FROM trips t
    JOIN trip_reservations tr ON t.trip_id = tr.trip_id
    JOIN client_reservations cr ON tr.ticket_id = cr.ticket_id
    JOIN clients c ON cr.client_id = c.client_id
    JOIN trip_connections tc ON t.trip_id = tc.trip_id
    LEFT JOIN connections conn1 ON conn1.route_id = tc.route1_id
    LEFT JOIN connections conn2 ON conn2.route_id = tc.route2_id
    LEFT JOIN connections conn3 ON conn3.route_id = tc.route3_id
    WHERE t.trip_id IN (
        SELECT DISTINCT t2.trip_id
        FROM trips t2
        JOIN trip_reservations tr2 ON t2.trip_id = tr2.trip_id
        JOIN client_reservations cr2 ON tr2.ticket_id = cr2.ticket_id
        WHERE cr2.client_id = ?
    )
    ORDER BY t.trip_id, c.client_id;
""";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, client_id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String tripIdStr = rs.getString("trip_id");
                UUID tripId = tripIdStr != null ? UUID.fromString(tripIdStr) : null;

                // Check if this trip already exists in the list
                Trip existingTrip = trips.stream()
                        .filter(t -> t.getTripId().equals(tripId))
                        .findFirst()
                        .orElse(null);

                if (existingTrip == null) {
                    // Create a new trip
                    List<Reservation> reservations = new ArrayList<>();
                    reservations.add(new Reservation(
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getInt("age"),
                            rs.getString("client_id")
                    ));

                    // Create route1, route2, route3
                    TrainRoute route1 = new TrainRoute(
                            rs.getString("route1_id"),
                            rs.getString("route1_departure_city"),
                            rs.getString("route1_arrival_city"),
                            rs.getString("route1_departure_time"),
                            rs.getString("route1_arrival_time"),
                            rs.getString("route1_train_type"),
                            rs.getString("route1_days_of_operation"),
                            rs.getInt("route1_first_class_ticket_rate"),
                            rs.getInt("route1_second_class_ticket_rate")
                    );

                    TrainRoute route2 = new TrainRoute(
                            rs.getString("route2_id"),
                            rs.getString("route2_departure_city"),
                            rs.getString("route2_arrival_city"),
                            rs.getString("route2_departure_time"),
                            rs.getString("route2_arrival_time"),
                            rs.getString("route2_train_type"),
                            rs.getString("route2_days_of_operation"),
                            rs.getInt("route2_first_class_ticket_rate"),
                            rs.getInt("route2_second_class_ticket_rate")
                    );

                    TrainRoute route3 = new TrainRoute(
                            rs.getString("route3_id"),
                            rs.getString("route3_departure_city"),
                            rs.getString("route3_arrival_city"),
                            rs.getString("route3_departure_time"),
                            rs.getString("route3_arrival_time"),
                            rs.getString("route3_train_type"),
                            rs.getString("route3_days_of_operation"),
                            rs.getInt("route3_first_class_ticket_rate"),
                            rs.getInt("route3_second_class_ticket_rate")
                    );

                    TrainConnection connection = new TrainConnection(route1, route2, route3);
                    trips.add(new Trip(tripId, reservations, connection));

                } else {
                    // Add an additional reservation to the same trip
                    existingTrip.addReservation(
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getInt("age"),
                            rs.getString("client_id")
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error while fetching trips", e);
        }
        return trips;
    }

    }

