package exceptions;

public class InvalidDriverIdException extends IllegalArgumentException{
    public InvalidDriverIdException(String errorMessage){
        super(errorMessage);
    }
}