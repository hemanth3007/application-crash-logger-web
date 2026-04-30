package model;

public class CrashLog {
    private String crashId;
    private String crashType;
    private String message;

    public CrashLog(String crashId, String crashType, String message) {
        this.crashId = crashId;
        this.crashType = crashType;
        this.message = message;
    }

    public String getCrashId() {
        return crashId;
    }

    public String getCrashType() {
        return crashType;
    }
    
    public String getMessage() {
        return message;
    }
}