package exceptions;

public class InvalidPositionException extends Exception {
    public InvalidPositionException(String errorMessage){
        super(errorMessage);
    }
}