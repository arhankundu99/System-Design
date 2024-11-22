package model;

import java.util.Date;

public class Log {
    private final String message;
    private final LogLevel level;
    private final long timestamp;
    public Log(String message, LogLevel level) {
        this.message = message;
        this.level = level;
        this.timestamp = (new Date()).getTime();
    }

    public String getMessage() {
        return message;
    }

    public LogLevel getLevel() {
        return level;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Log [message = " + message + ", level = " + level + ", timestamp = " + timestamp + "]";
    }
}
