package models;

import models.pieces.Piece;

public class Cell {
    Piece piece;
    public Cell(Piece piece) {
        this.piece = piece;
    }

    public Cell() {
        this.piece = null;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() {
        return piece;
    }
}
