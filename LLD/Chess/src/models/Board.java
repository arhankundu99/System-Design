package models;

import models.pieces.Piece;

public class Board {
    Cell[][] cells;
    public static final int NUM_ROWS = 8;
    public static final int NUM_COLS = 8;
    public Board() {
        cells = new Cell[NUM_ROWS][NUM_COLS];
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    public void setPiece(int row, int col, Piece piece) {
        cells[row][col].setPiece(piece);
    }

    public Cell getCell(int row, int col) {
        return cells[row][col];
    }

    public Piece getPiece(int row, int col) {
        return cells[row][col].getPiece();
    }

    public boolean isCheckMatePosition() {
        return false;
    }

    public boolean isStateMatePosition() {
        return false;
    }

    public boolean isDrawPosition() {
        return false;
    }

    public boolean isCheckPosition() {
        return false;
    }
}
