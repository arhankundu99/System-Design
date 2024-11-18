package models;
import exceptions.*;
import utils.*;

public class Rook extends Piece {
    int row;
    int col;
    boolean isWhite;

    public Rook(int row, int col, boolean isWhite) {
        super(row, col, isWhite);
    }

    public boolean canMove(int destRow, int destCol, Board board) {
        if (Utils.isOutOfBounds(destRow, destCol)) {
            throw new InvalidPositionException("The destination position of rook is out of bounds.")
        }

        if (board.get(destRow, destCol) != null && board.get(destRow, destCol).isWhite == this.isWhite) {
            return false;
        }
        // Check horizontal
        if (row == destRow && col != destCol) {

            if (destCol > col) {
                for (int i = col + 1; i < destCol; i++) {
                    if (board.get(row, i) != null) {
                        return false;
                    }
                }
            } else {
                for (int i = col - 1; i > destCol; i--) {
                    if (board.get(row, i) != null) {
                        return false;
                    }
                }
            }
        }

        // Check vertical
        if (col == destCol && row != destRow) {

            if (destRow > row) {
                for (int i = row + 1; i < destRow; i++) {
                    if (board.get(i, col) != null) {
                        return false;
                    }
                }
            } else {
                for (int i = row - 1; i > destRow; i--) {
                    if (board.get(i, col) != null) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}