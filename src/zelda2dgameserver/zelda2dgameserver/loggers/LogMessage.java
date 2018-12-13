package zelda2dgameserver.loggers;

public class LogMessage {

    private int level;
    private String message;

    public LogMessage(int level, String message) {
        this.level = level;
        this.message = message;
    }

    public int getLevel() {
        return level;
    }

    public String getMessage() {
        return message;
    }
}
