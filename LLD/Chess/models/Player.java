package models;
import exceptions.*;

public class Player {
    boolean isWhite;
    
    public Player(boolean isWhite) {
        this.isWhite = isWhite;
    }
    boolean move(Piece piece, int destRow, int destCol, Board board) {
        if (piece == null || piece.isWhite != this.isWhite) {
            throw InvalidChosenPieceException("Please choose a valid piece of your assigned color");
        }

        if (piece.canMove(destRow, destCol, board)) {
            board.set(destRow, destCol, piece);
            return true;
        }

        throw InvalidPositionException("The destination position is not a valid position");
    }

}