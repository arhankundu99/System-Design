package models;
import exceptions.*;
import utils.*;

public class Pawn extends Piece {
    int row;
    int col;
    boolean isWhite;

    public Pawn(int row, int col, boolean isWhite) {
        super(row, col, isWhite);
    }

    public boolean canMove(int destRow, int destCol, Board board) {
        if (Utils.isOutOfBounds(destRow, destCol)) {
            throw new InvalidPositionException("The destination position of Pawn is out of bounds.")
        }
        
        if (board.get(destRow, destCol) == null) {
            if (isWhite) {
                if (destRow == row + 1) {
                    return true;
                }
            } else {
                if (destRow == row - 1) {
                    return true;
                }
            }
        }


        if (board.get(destRow, destCol) != null && board.get(destRow, destCol).isWhite == !this.isWhite) {
            if (isWhite) {
                if (destRow == row + 1 && (destCol == col - 1 || destCol == col + 1)) {
                    return true;
                }
            } else {
                if (destRow == row - 1 && ((destCol == col - 1 || destCol == col + 1))) {
                    return true;
                }
            }
        }
        return false;
    }
}