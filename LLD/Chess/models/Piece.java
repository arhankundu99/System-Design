package models;
public abstract class Piece {
    int row;
    int col;
    boolean isWhite;

    public Piece(int row, int col, boolean isWhite) {
        if (isOutOfBounds(row, col)) {
            throw new InvalidPositionException("The position of piece is out of bounds.")
        }
        this.row = row;
        this.col = col;
        this.isWhite = isWhite;
    }

    public abstract boolean canMove(int destRow, int destCol, Board board);

    public boolean isOutOfBounds(int row, int col) {
        return (row >= 8 || col >= 8 || row < 0 || col < 0);
    }
}