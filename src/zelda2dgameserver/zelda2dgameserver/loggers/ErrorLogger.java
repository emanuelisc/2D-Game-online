package zelda2dgameserver.loggers;

import java.util.Date;

public class ErrorLogger extends LoggerChain {
    private String color = "";

    @Override
    public void logMessage(LogMessage message) {
        if (message.getLevel() == Logger.LVL_ERROR) {
            System.out.println(String.format("\u001B[31m[%s] ERROR: %s\033[0m", new Date().toString(), message.getMessage()));
        } else if (nextChain != null) {
            nextChain.logMessage(message);
        }
    }
}
