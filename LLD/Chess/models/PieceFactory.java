package models;

public class PieceFactory {
    public Piece getPiece(PieceType pieceType, int row, int col, boolean isWhite) {
        switch (pieceType) {
            case ROOK:
                return new Rook(row, col, isWhite);
            case KNIGHT:
                return new Knight(row, col, isWhite);
            case BISHOP:
                return new Bishop(row, col, isWhite);
            case KING:
                return new King(row, col, isWhite);
            case QUEEN:
                return new Queen(row, col, isWhite);
            case PAWN:
                return new Pawn(row, col, isWhite);
            default:
                throw new IllegalArgumentException("Invalid PieceType: " + pieceType);
        }
    }
}