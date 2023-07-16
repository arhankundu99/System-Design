package exceptions;

public class InvalidRiderIdException extends IllegalArgumentException{
    public InvalidRiderIdException(String errorMessage){
        super(errorMessage);
    }
}