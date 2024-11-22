package models.pieces;

import exceptions.CellOutOfBoundException;
import models.Board;
import models.Color;

public abstract class Piece {
    PieceType type;
    Color color;
    int row;
    int col;
    public Piece(PieceType type, Color color, int row, int col) throws CellOutOfBoundException {
        this.type = type;
        this.color = color;

        if (row < 0 || col < 0 || row >= Board.NUM_ROWS || col >= Board.NUM_COLS) {
            throw new CellOutOfBoundException("The given position is out of bounds to create a piece.");
        }
        this.row = row;
        this.col = col;
    }

    public PieceType getType() {
        return type;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Color getColor() {
        return color;
    }

    public void setRow(int row) throws CellOutOfBoundException {
        if (row < 0 || row >= Board.NUM_ROWS) {
            throw new CellOutOfBoundException("The given position is out of bounds to create a piece.");
        }
        this.row = row;
    }

    public void setCol(int col) throws CellOutOfBoundException {
        if (col < 0 || col >= Board.NUM_COLS) {
            throw new CellOutOfBoundException("The given position is out of bounds to create a piece.");
        }
        this.col = col;
    }

    public abstract boolean canMove(int destX, int destY, Board board) throws CellOutOfBoundException;
}
