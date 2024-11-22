package exceptions;

public class LockAlreadyAcquiredException extends Exception {
    public LockAlreadyAcquiredException(String errorMessage) {
        super(errorMessage);
    }
}