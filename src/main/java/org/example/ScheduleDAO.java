package org.example;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDAO {

    // Використовуємо твій DatabaseConnection.connect()
    private Connection getConnection() throws SQLException {
        return DatabaseConnection.connect();
    }

    public List<Schedule> getAllSchedules() {
        List<Schedule> list = new ArrayList<>();
        String sql = "SELECT * FROM schedule ORDER BY id";

        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Timestamp depTs = rs.getTimestamp("departure_time");
                Timestamp arrTs = rs.getTimestamp("arrival_time");

                LocalDateTime dep = depTs != null ? depTs.toLocalDateTime() : null;
                LocalDateTime arr = arrTs != null ? arrTs.toLocalDateTime() : null;

                Schedule s = new Schedule(
                        rs.getInt("id"),
                        rs.getInt("route_id"),
                        rs.getInt("bus_id"),
                        rs.getInt("driver_id"),
                        dep,
                        arr
                );
                list.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void addSchedule(Schedule s) {
        String sql = "INSERT INTO schedule(route_id, bus_id, driver_id, departure_time, arrival_time) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, s.getRouteId());
            ps.setInt(2, s.getBusId());
            ps.setInt(3, s.getDriverId());
            ps.setTimestamp(4, Timestamp.valueOf(s.getDepartureTime()));
            ps.setTimestamp(5, Timestamp.valueOf(s.getArrivalTime()));
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ОНОВЛЕННЯ (updateSchedule) — саме те, чого не вистачало
    public void updateSchedule(Schedule s) {
        String sql = "UPDATE schedule SET route_id = ?, bus_id = ?, driver_id = ?, departure_time = ?, arrival_time = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, s.getRouteId());
            ps.setInt(2, s.getBusId());
            ps.setInt(3, s.getDriverId());
            ps.setTimestamp(4, Timestamp.valueOf(s.getDepartureTime()));
            ps.setTimestamp(5, Timestamp.valueOf(s.getArrivalTime()));
            ps.setInt(6, s.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteSchedule(int id) {
        String sql = "DELETE FROM schedule WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
