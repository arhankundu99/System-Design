package exceptions;

public class InvalidStateException extends Exception {
    public InvalidStateException(String errorMessage) {
        super(errorMessage);
    }
}