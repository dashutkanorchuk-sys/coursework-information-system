package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RouteDAO {

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/bus",
                "darianorchuk",
                "12212005"
        );
    }

    public List<Route> getAllRoutes() {
        List<Route> list = new ArrayList<>();

        String sql = "SELECT * FROM route ORDER BY id";

        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Route(
                        rs.getInt("id"),
                        rs.getString("start_point"),
                        rs.getString("end_point"),
                        rs.getDouble("distance")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void addRoute(Route r) {
        String sql = "INSERT INTO route(start_point, end_point, distance) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, r.getStartPoint());
            ps.setString(2, r.getEndPoint());
            ps.setDouble(3, r.getDistance());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateRoute(Route r) {
        String sql = "UPDATE route SET start_point=?, end_point=?, distance=? WHERE id=?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, r.getStartPoint());
            ps.setString(2, r.getEndPoint());
            ps.setDouble(3, r.getDistance());
            ps.setInt(4, r.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteRoute(int id) {
        String sql = "DELETE FROM route WHERE id=?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
