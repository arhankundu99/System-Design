package exceptions;

public class InvalidTripIdException extends IllegalArgumentException{
    public InvalidTripIdException(String errorMessage){
        super(errorMessage);
    }
}