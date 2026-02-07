package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DriverDAO {

    public void addDriver(Driver driver) {
        String sql = "INSERT INTO driver (name, license_number, phone) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, driver.getName());
            stmt.setString(2, driver.getLicense());
            stmt.setString(3, driver.getPhone());
            stmt.executeUpdate();

            System.out.println("Driver added successfully.");

        } catch (SQLException e) {
            System.out.println("Error adding driver: " + e.getMessage());
        }
    }

    public List<Driver> getAllDrivers() {
        List<Driver> drivers = new ArrayList<>();
        String sql = "SELECT * FROM driver";

        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                drivers.add(new Driver(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("license_number")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error loading drivers: " + e.getMessage());
        }

        return drivers;
    }

    public void updateDriver(Driver driver) {
        String sql = "UPDATE driver SET name=?, license_number=?, phone=? WHERE id=?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, driver.getName());
            stmt.setString(2, driver.getLicense());
            stmt.setString(3, driver.getPhone());
            stmt.setInt(4, driver.getId());
            stmt.executeUpdate();

            System.out.println("Driver updated successfully.");

        } catch (SQLException e) {
            System.out.println("Error updating driver: " + e.getMessage());
        }
    }

    public void deleteDriver(int id) {
        String sql = "DELETE FROM driver WHERE id=?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

            System.out.println("Driver deleted.");

        } catch (SQLException e) {
            System.out.println("Error deleting driver: " + e.getMessage());
        }
    }
}
