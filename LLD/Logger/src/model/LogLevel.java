package model;

public enum LogLevel {
    DEBUG(0),
    INFO(1),
    WARNING(2),
    ERROR(3),
    FATAL(4);

    private int value;
    public int getValue() {
        return value;
    }

    LogLevel(int value) {
        this.value = value;
    }
}
