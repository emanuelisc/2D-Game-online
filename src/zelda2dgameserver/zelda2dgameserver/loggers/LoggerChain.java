package zelda2dgameserver.loggers;

abstract class LoggerChain {

    protected LoggerChain nextChain;

    public void setNextChain(LoggerChain nextChain) {
        this.nextChain = nextChain;
    }

    public abstract void logMessage(LogMessage message);

}
