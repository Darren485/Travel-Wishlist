package dev;

import java.sql.*;
import java.util.*;

/**
 * Data Access Object (DAO) for Travel entities. Handles all database operations
 * for travel wishlist items.
 */
public class TravelDAO {

    /**
     * Adds a new travel destination to the database.
     *
     * @param travel the Travel object containing destination details
     * @return true if the travel was added successfully, false otherwise
     */
    public boolean addTravel(Travel travel) {
        String sql = "INSERT INTO travels (country, season, priority, name, budget) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = TravelDB.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, travel.getCountry());
            pstmt.setString(2, travel.getSeason().name());
            pstmt.setString(3, travel.getPriority().name());
            pstmt.setString(4, travel.getName());
            pstmt.setBigDecimal(5, travel.getBudget());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves a single travel destination by its ID.
     *
     * @param id the unique identifier of the travel destination
     * @return the Travel object if found, null otherwise
     */
    public Travel getTravelById(int id) {
        String sql = "SELECT * FROM travels WHERE id = ?";
        try (Connection conn = TravelDB.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToTravel(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all travel destinations from the database.
     *
     * @return a List of all Travel objects, or an empty list if none exist
     */
    public List<Travel> getAllTravels() {
        String sql = "SELECT * FROM travels";
        List<Travel> travels = new ArrayList<>();
        try (Connection conn = TravelDB.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                travels.add(mapResultSetToTravel(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return travels;
    }

    /**
     * Updates an existing travel destination in the database.
     *
     * @param travel the Travel object with updated information
     * @return true if the travel was updated successfully, false otherwise
     */
    public boolean updateTravel(Travel travel) {
        String sql = "UPDATE travels SET country = ?, season = ?, priority = ?, name = ?, budget = ? WHERE id = ?";
        try (Connection conn = TravelDB.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, travel.getCountry());
            pstmt.setString(2, travel.getSeason().name());
            pstmt.setString(3, travel.getPriority().name());
            pstmt.setString(4, travel.getName());
            pstmt.setBigDecimal(5, travel.getBudget());
            pstmt.setInt(6, travel.getId());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes a travel destination from the database by its ID.
     *
     * @param id the unique identifier of the travel destination to delete
     * @return true if the travel was deleted successfully, false otherwise
     */
    public boolean deleteTravel(int id) {
        String sql = "DELETE FROM travels WHERE id = ?";
        try (Connection conn = TravelDB.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Maps a ResultSet row to a Travel object. Helper method to convert
     * database records into Travel entity objects.
     *
     * @param rs the ResultSet containing travel data
     * @return a Travel object populated with data from the ResultSet
     * @throws SQLException if a database access error occurs
     */
    private Travel mapResultSetToTravel(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String country = rs.getString("country");
        String season = rs.getString("season");
        String priority = rs.getString("priority");
        String name = rs.getString("name");
        var budget = rs.getBigDecimal("budget");

        // Create and populate Travel object
        Travel travel = new Travel();
        travel.setId(id);
        travel.setCountry(country);
        travel.setSeason(Season.valueOf(season));
        travel.setPriority(Priority.valueOf(priority));
        travel.setName(name);
        travel.setBudget(budget);
        return travel;
    }

    /**
     * Saves changes to the database. Can be extended to implement batch
     * operations or transaction management.
     */
    public void save() {
        // TODO: Implement batch operations or persistence logic if needed
    }
}
