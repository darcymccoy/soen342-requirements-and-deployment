package main.dao;

import main.model.TrainRoute;
import main.util.DBHelper;

import java.sql.*;
import java.util.*;

public class TrainRouteDAO {

    private static final String TABLE = "connections";

    // Reusable method for dynamic filters
    private List<TrainRoute> executeQuery(String baseQuery, Map<String, Object> filters) {
        StringBuilder sql = new StringBuilder(baseQuery);
        List<Object> values = new ArrayList<>();

        if (filters != null && !filters.isEmpty()) {
            sql.append(" WHERE 1=1");
            for (Map.Entry<String, Object> entry : filters.entrySet()) {
                sql.append(" AND ").append(entry.getKey()).append(" = ?");
                values.add(entry.getValue());
            }
        }

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < values.size(); i++) {
                stmt.setObject(i + 1, values.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            List<TrainRoute> routes = new ArrayList<>();

            while (rs.next()) {
                routes.add(new TrainRoute(
                        rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getString(7),
                        rs.getInt(8), rs.getInt(9)
                ));
            }
            return routes;

        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    // Public DAO methods
    public List<TrainRoute> findArrivals(String city) {
        Map<String, Object> filters = new HashMap<>();
        filters.put("`arrival_city`", city);
        return executeQuery("SELECT * FROM " + TABLE, filters);
    }

    public List<TrainRoute> findDepartures(String city) {
        Map<String, Object> filters = new HashMap<>();
        filters.put("`departure_city`", city);
        return executeQuery("SELECT * FROM " + TABLE, filters);
    }

    public List<TrainRoute> findDepartureArrivalPair(String depCity, String arrCity, Map<String, Object> additionalFilters) {
        Map<String, Object> filters = new HashMap<>();
        filters.put("`departure_city`", depCity);
        filters.put("`arrival_city`", arrCity);
        if (additionalFilters != null && !additionalFilters.isEmpty()) {
            filters.putAll(additionalFilters);
        }
        return executeQuery("SELECT * FROM " + TABLE, filters);
    }

    public ArrayList<String> listAllCities() {
        String sql = String.format("""
            SELECT `departure_city` AS City FROM %s
            UNION
            SELECT `arrival_city` AS City FROM %s
        """, TABLE, TABLE);

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            ArrayList<String> cities = new ArrayList<>();
            while (rs.next()) cities.add(rs.getString("City"));
            return cities;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}