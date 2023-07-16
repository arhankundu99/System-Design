package exceptions;

public class NoAvailableDriversException extends RuntimeException{
    public NoAvailableDriversException(String errorMessage){
        super(errorMessage);
    }
}