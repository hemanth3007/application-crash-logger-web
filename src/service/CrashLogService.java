package service;

import dao.CrashLogDAO;
import model.CrashLog;
import java.util.List;

public class CrashLogService {

    private CrashLogDAO dao = new CrashLogDAO();

    public String addCrashLog(String id, String fullMsg) {
        if (id == null || id.trim().isEmpty() || fullMsg == null || fullMsg.trim().isEmpty()) return "Invalid Input: ID and Message cannot be empty.";
        if (!fullMsg.contains(":")) return "Invalid Input: Message must contain ':' separating type and details.";
        String[] parts = fullMsg.split(":", 2);
        String type = parts[0].trim();
        String message = parts[1].trim();
        try {
            boolean success = dao.addLog(id.trim(), type, message);
            return success ? "Success" : "Invalid or Duplicate ID.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Database Error: " + e.getMessage();
        }
    }

    public String getAllCrashLogs() {
        return format(dao.getAllLogs());
    }

    public String getFilteredCrashLogs() {
        return format(dao.getFilteredLogs());
    }

    public String getSortedCrashLogs() {
        return format(dao.getSortedLogs());
    }

    private String format(List<CrashLog> logs) {
        StringBuffer sb = new StringBuffer();
        for (CrashLog log : logs) {
            sb.append("{\n\tCrashId: ")
              .append(log.getCrashId())
              .append(" | Details: ")
              .append(log.getCrashType())
              .append(":")
              .append(log.getMessage().replace("\n", "\n\t"))
              .append("\n}\n");
        }
        return sb.toString().trim();
    }
}