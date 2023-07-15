package exceptions;

public class InvalidBoardEntityException extends IllegalArgumentException {
    public InvalidBoardEntityException(String errorMessage){
        super(errorMessage);
    }
}