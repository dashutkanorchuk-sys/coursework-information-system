package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO {

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/bus",
                "darianorchuk",
                "12212005"
        );
    }

    public List<Ticket> getAllTickets() {
        List<Ticket> list = new ArrayList<>();

        String sql = "SELECT * FROM ticket ORDER BY id";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Ticket t = new Ticket(
                        rs.getInt("id"),
                        rs.getInt("passenger_id"),
                        rs.getInt("route_id"),
                        rs.getInt("bus_id"),
                        rs.getDate("departure_date").toLocalDate(),
                        rs.getDouble("price"),
                        rs.getString("status")
                );
                list.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void addTicket(Ticket t) {
        String sql = "INSERT INTO ticket (passenger_id, route_id, bus_id, departure_date, price, status) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, t.getPassengerId());
            ps.setInt(2, t.getRouteId());
            ps.setInt(3, t.getBusId());
            ps.setDate(4, java.sql.Date.valueOf(t.getDepartureDate())); // FIX ✔
            ps.setDouble(5, t.getPrice());
            ps.setString(6, t.getStatus());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateTicket(Ticket t) {
        String sql = "UPDATE ticket SET passenger_id=?, route_id=?, bus_id=?, departure_date=?, price=?, status=? WHERE id=?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, t.getPassengerId());
            ps.setInt(2, t.getRouteId());
            ps.setInt(3, t.getBusId());
            ps.setDate(4, java.sql.Date.valueOf(t.getDepartureDate())); // FIX ✔
            ps.setDouble(5, t.getPrice());
            ps.setString(6, t.getStatus());
            ps.setInt(7, t.getId());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteTicket(int id) {
        String sql = "DELETE FROM ticket WHERE id=?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
