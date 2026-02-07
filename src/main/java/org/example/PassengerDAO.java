package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PassengerDAO {

    public void addPassenger(Passenger passenger) {
        String sql = "INSERT INTO passenger(name, phone, email) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, passenger.getName());
            stmt.setString(2, passenger.getPhone());
            stmt.setString(3, passenger.getEmail());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Passenger> getAllPassengers() {
        List<Passenger> list = new ArrayList<>();
        String sql = "SELECT * FROM passenger ORDER BY id";

        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Passenger(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void updatePassenger(Passenger passenger) {
        String sql = "UPDATE passenger SET name=?, phone=?, email=? WHERE id=?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, passenger.getName());
            stmt.setString(2, passenger.getPhone());
            stmt.setString(3, passenger.getEmail());
            stmt.setInt(4, passenger.getId());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletePassenger(int id) {
        String sql = "DELETE FROM passenger WHERE id=?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
