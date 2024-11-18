package models;
import exceptions.*;
import utils.*;

public class Bishop extends Piece {
    int row;
    int col;
    boolean isWhite;

    public Bishop(int row, int col, boolean isWhite) {
        super(row, col, isWhite);
    }

    public boolean canMove(int destRow, int destCol, Board board) {
        if (Utils.isOutOfBounds(destRow, destCol)) {
            throw new InvalidPositionException("The destination position of Bishop is out of bounds.")
        }

        if (board.get(destRow, destCol) != null && board.get(destRow, destCol).isWhite == this.isWhite) {
            return false;
        }

        // Check upper right
        if (destRow - row == destCol - col) {
            for (int i = 1; i < destRow - row; i++) {
                if (board.get(row + i, col + i) != null) {
                    return false;
                }
            } 
        }

        // Check upper left
        if (destRow - row == col - destCol) {
            for (int i = 1; i < destRow - row; i++) {
                if (board.get(row + i, destCol + i) != null) {
                    return false;
                }
            }
        }

        // Check lower left
        if (destRow - row == destCol - col) {
            for (int i = 1; i < row - destRow; i++) {
                if (board.get(destRow + i, destCol + i) != null) {
                    return false;
                }
            }
        }

        // Check lower right (row > destRow and col < destCol)
        if (row - destRow == destCol - col) {
            for (int i = 1; i < row - destRow; i++) {
                if (board.get(row + i, destCol + i) != null) {
                    return false;
                }
            }
        }
        return true;
    }
}