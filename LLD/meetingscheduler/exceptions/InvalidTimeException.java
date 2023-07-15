package exceptions;
public class InvalidTimeException extends RuntimeException{
    public InvalidTimeException(String errorMessage){
        super(errorMessage);
    }
}