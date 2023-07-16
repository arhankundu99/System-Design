package exceptions;

public class InvalidNumberOfSeatsException extends IllegalArgumentException{
    public InvalidNumberOfSeatsException(String errorMessage){
        super(errorMessage);
    }
}