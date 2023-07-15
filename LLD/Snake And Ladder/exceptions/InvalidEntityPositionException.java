package exceptions;

public class InvalidEntityPositionException extends IllegalArgumentException {
    public InvalidEntityPositionException(String errorMessage){
        super(errorMessage);
    }
}