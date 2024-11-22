package exceptions;

public class NoFreeSlotAvailableException extends Exception {
    public NoFreeSlotAvailableException(String message) {
        super(message);
    }
}
