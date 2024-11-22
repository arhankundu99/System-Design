package models;

public class Response {
    private final String message;
    private final int offset;

    public Response(String message, int offset) {
        this.message = message;
        this.offset = offset;
    }

    public String getMessage() {
        return message;
    }

    public int getOffset() {
        return offset;
    }

    @Override
    public String toString() {
        return "[Offset]: " + offset + ", [Message]: " + message;
    }
}
