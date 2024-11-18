package exceptions;

public class InvalidChosenPieceException extends Exception {
    public InvalidChosenPieceException(String errorMessage) {
        super(errorMessage);
    }
}