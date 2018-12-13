package zelda2dgameserver.loggers;

public class Logger {
    public static final int LVL_USER_ACTIVITY = 0;
    public static final int LVL_ERROR = 1;

    private LoggerChain loggerChain;

    public Logger() {
        // Chaining loggers
        loggerChain = new UserActivityLogger();
        loggerChain.setNextChain(new ErrorLogger());
    }

    public void logMessage(LogMessage message) {
        loggerChain.logMessage(message);
    }
}
