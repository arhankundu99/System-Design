package services;

import mode.AppendMode;
import model.Log;
import model.LogLevel;

public class LoggingService {
    private AppendMode appendMode;
    private LogLevel logLevel;
    public LoggingService(AppendMode appendMode, LogLevel logLevel) {
        this.appendMode = appendMode;
        this.logLevel = logLevel;
    }

    public void setAppendMode(AppendMode appendMode) {
        this.appendMode = appendMode;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    public void log(LogLevel level, String message) {
        if (level.getValue() >= this.logLevel.getValue()) {
            Log log = new Log(message, level);
            appendMode.append(log);
        }
    }

    public void debug(String message) {
        log(LogLevel.DEBUG, message);
    }

    public void info(String message) {
        log(LogLevel.INFO, message);
    }

    public void warn(String message) {
        log(LogLevel.WARNING, message);
    }

    public void error(String message) {
        log(LogLevel.ERROR, message);
    }

    public void fatal(String message) {
        log(LogLevel.FATAL, message);
    }
}
