package dao;

import model.CrashLog;
import java.sql.*;
import java.util.*;

public class CrashLogDAO {
    public boolean addLog(String id, String type, String message) throws Exception {
        try (Connection con = DBConnection.getConnection()) {
            // check duplicate
            PreparedStatement check = con.prepareStatement(
                "SELECT * FROM crash_logs WHERE crash_id=?"
            );
            check.setString(1, id);

            ResultSet rs = check.executeQuery();
            if (rs.next()) return false;

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO crash_logs (crash_id, crash_type, details) VALUES (?, ?, ?)"
            );
            ps.setString(1, id);
            ps.setString(2, type);
            ps.setString(3, message);

            ps.executeUpdate();

            ResultSet r = con.createStatement().executeQuery("SELECT DATABASE()");
            if (r.next()) 
                System.out.println("DB NAME = " + r.getString(1));
            return true;
        }
    }

    // Display All Logs
    public List<CrashLog> getAllLogs() {
        List<CrashLog> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM crash_logs ORDER BY created_at");
            System.out.println("Running query...");

            while (rs.next()) {
                 System.out.println("ROW FOUND: " + rs.getString("crash_id"));
                list.add(new CrashLog(
                    rs.getString("crash_id"),
                    rs.getString("crash_type"),
                    rs.getString("details")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Filter unexpected logs (Unexpected)
    public List<CrashLog> getFilteredLogs() {
        List<CrashLog> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(
                "SELECT * FROM crash_logs WHERE LOWER(details) LIKE '%unexpected%'"
            );

            while (rs.next()) {
                list.add(new CrashLog(
                    rs.getString("crash_id"),
                    rs.getString("crash_type"),
                    rs.getString("details")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Sort the logs by ID (ascending)
    public List<CrashLog> getSortedLogs() {
        List<CrashLog> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(
                "SELECT * FROM crash_logs ORDER BY crash_id"
            );

            while (rs.next()) {
                list.add(new CrashLog(
                    rs.getString("crash_id"),
                    rs.getString("crash_type"),
                    rs.getString("details")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}