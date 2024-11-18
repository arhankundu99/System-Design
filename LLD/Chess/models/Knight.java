package models;
import exceptions.*;
import utils.*;

public class Knight extends Piece {
    int row;
    int col;
    boolean isWhite;

    public Knight(int row, int col, boolean isWhite) {
        super(row, col, isWhite);
    }

    public boolean canMove(int destRow, int destCol, Board board) {
        if (Utils.isOutOfBounds(destRow, destCol)) {
            throw new InvalidPositionException("The destination position of Knight is out of bounds.")
        }

        if (board.get(destRow, destCol) != null && board.get(destRow, destCol).isWhite == this.isWhite) {
            return false;
        }
        
        if (destRow == row - 1) {
            if (destCol == col - 2 || destCol == col + 2) {
                return true;
            }
        }

        if (destRow == row - 2) {
            if (destCol == col - 1 || destCol == col + 1) {
                return true;
            }
        }

        if (destRow == row + 1) {
            if (destCol == col - 2 || destCol == col + 2) {
                return true;
            }
        }

        if (destRow == row + 2) {
            if (destCol == col - 1 || destCol == col + 1) {
                return true;
            }
        }

        return false;
    }
}