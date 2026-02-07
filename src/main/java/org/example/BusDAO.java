package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BusDAO {

    public void addBus(Bus bus) {
        String sql = "INSERT INTO bus(model, seats, plate_number) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, bus.getModel());
            stmt.setInt(2, bus.getSeats());
            stmt.setString(3, bus.getPlateNumber());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Bus> getAllBuses() {
        List<Bus> list = new ArrayList<>();
        String sql = "SELECT * FROM bus ORDER BY id";

        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Bus(
                        rs.getInt("id"),
                        rs.getString("model"),
                        rs.getInt("seats"),
                        rs.getString("plate_number")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void updateBus(Bus bus) {
        String sql = "UPDATE bus SET model=?, seats=?, plate_number=? WHERE id=?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, bus.getModel());
            stmt.setInt(2, bus.getSeats());
            stmt.setString(3, bus.getPlateNumber());
            stmt.setInt(4, bus.getId());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteBus(int id) {
        String sql = "DELETE FROM bus WHERE id=?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
