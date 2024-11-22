package models.pieces;

import exceptions.CellOutOfBoundException;
import models.Board;
import models.Color;

public class Pawn extends Piece {
    public Pawn(Color color, int row, int col) throws CellOutOfBoundException {
        super(PieceType.PAWN, color, row, col);
    }
    public boolean canMove(int destRow, int destCol, Board board) throws CellOutOfBoundException {
        if (destRow < 0 || destCol < 0 || destRow >= Board.NUM_ROWS || destCol >= Board.NUM_COLS) {
            throw new CellOutOfBoundException("The given cell positions are invalid.");
        }

        Piece piece = board.getPiece(destRow, destCol);
        if (color == Color.WHITE) {
            if (destRow == row + 1) {
                if (destCol == col) {
                    return piece == null;
                } else if (destCol == col - 1) {
                    return piece.getColor() == Color.BLACK;
                } else if (destCol == col + 1) {
                    return piece.getColor() == Color.BLACK;
                }
            }
        } else {
            if (destRow == row - 1) {
                if (destCol == col) {
                    return piece == null;
                } else if (destCol == col + 1) {
                    return piece.getColor() == Color.WHITE;
                } else if (destCol == col - 1) {
                    return piece.getColor() == Color.WHITE;
                }
            }
        }
        return false;
    }
}
