package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaintenanceRequestDAO {

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/bus",
                "darianorchuk",
                "12212005"
        );
    }

    public List<MaintenanceRequest> getAllRequests() {
        List<MaintenanceRequest> list = new ArrayList<>();

        String sql = "SELECT * FROM maintenance_request ORDER BY id";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                MaintenanceRequest r = new MaintenanceRequest(
                        rs.getInt("id"),
                        rs.getInt("bus_id"),
                        rs.getDate("request_date").toLocalDate(),
                        rs.getString("problem"),
                        rs.getString("priority"),
                        rs.getString("status")
                );
                list.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void addRequest(MaintenanceRequest r) {
        String sql = "INSERT INTO maintenance_request (bus_id, request_date, problem, priority, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, r.getBusId());
            ps.setDate(2, java.sql.Date.valueOf(r.getRequestDate())); // FIX ✔
            ps.setString(3, r.getProblem());
            ps.setString(4, r.getPriority());
            ps.setString(5, r.getStatus());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateRequest(MaintenanceRequest r) {
        String sql = "UPDATE maintenance_request SET bus_id=?, request_date=?, problem=?, priority=?, status=? WHERE id=?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, r.getBusId());
            ps.setDate(2, java.sql.Date.valueOf(r.getRequestDate())); // FIX ✔
            ps.setString(3, r.getProblem());
            ps.setString(4, r.getPriority());
            ps.setString(5, r.getStatus());
            ps.setInt(6, r.getId());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteRequest(int id) {
        String sql = "DELETE FROM maintenance_request WHERE id=?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
