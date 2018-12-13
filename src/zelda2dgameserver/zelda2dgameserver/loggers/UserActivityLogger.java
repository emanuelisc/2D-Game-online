package zelda2dgameserver.loggers;

import java.util.Date;

public class UserActivityLogger extends LoggerChain {
    @Override
    public void logMessage(LogMessage message) {
        if (message.getLevel() == Logger.LVL_USER_ACTIVITY) {
            System.out.println(String.format("\u001B[33m[%s] USER ACTIVITY: %s\033[0m", new Date().toString(), message.getMessage()));
        } else if (nextChain != null) {
            nextChain.logMessage(message);
        }
    }
}
