package models;

import exceptions.CellOutOfBoundException;
import exceptions.InvalidChosenPieceException;
import exceptions.InvalidMoveException;
import models.pieces.Piece;

public class Player {
    private final String name;
    private final Color color;
    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
    }
    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public void move(Piece piece, int destRow, int destCol, Board board) throws InvalidChosenPieceException, CellOutOfBoundException, InvalidMoveException {
        if (destRow < 0 || destRow >= Board.NUM_ROWS || destCol < 0 || destCol >= Board.NUM_ROWS) {
            throw new CellOutOfBoundException("The cell numbers must be in bound");
        }
        if (piece == null) {
            throw new InvalidChosenPieceException("Please choose a valid piece.");
        }

        if (color != piece.getColor()) {
            throw new InvalidChosenPieceException("Please chose your chosen piece color.");
        }
        
        int sourceRow = piece.getRow();
        int sourceCol = piece.getCol();
        if (piece.canMove(destRow, destCol, board)) {
            board.setPiece(destRow, destCol, piece);
            board.setPiece(sourceRow, sourceCol, null);
            piece.setRow(destRow);
            piece.setCol(destCol);
        } else {
            throw new InvalidMoveException("Please choose a valid move.");
        }
    }
}
