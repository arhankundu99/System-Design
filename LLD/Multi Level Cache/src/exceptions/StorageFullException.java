package exceptions;

public class StorageFullException extends Exception {
    public StorageFullException(String message) {
        super(message);
    }
}
