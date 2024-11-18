package models;
import utils.*;
import exceptions.*;

public class Board {
    Piece[][] pieces;
    PieceFactory pieceFactory;

    Board() {
        pieces = new Pieces[8][8];
        pieceFactory = new PieceFactory();
    }

    void initBoard() {
        // Using Factory
        pieces[0][0] = pieceFactory.getPiece(PieceType.ROOK, 0, 0, false);
        pieces[0][1] = pieceFactory.getPiece(PieceType.KNIGHT, 0, 1, false);
        pieces[0][2] = pieceFactory.getPiece(PieceType.BISHOP, 0, 2, false);
        pieces[0][3] = pieceFactory.getPiece(PieceType.KING, 0, 3, false);
        pieces[0][4] = pieceFactory.getPiece(PieceType.QUEEN, 0, 4, false);
        pieces[0][5] = pieceFactory.getPiece(PieceType.BISHOP, 0, 5, false);
        pieces[0][6] = pieceFactory.getPiece(PieceType.KNIGHT, 0, 6, false);
        pieces[0][7] = pieceFactory.getPiece(PieceType.ROOK, 0, 7, false);


        pieces[7][0] = new Rook(7, 0, true);
        pieces[7][1] = new Knight(7, 1, true);
        pieces[7][2] = new Bishop(7, 2, true);
        pieces[7][3] = new King(7, 3, true);
        pieces[7][4] = new Queen(7, 4, true);
        pieces[7][5] = new Bishop(7, 5, true);
        pieces[7][6] = new Knight(7, 6, true);
        pieces[7][7] = new Rook(7, 7, true);

        for (int i = 0; i < 7; i++) {
            pieces[1][i] = new Pawn(1, i, false);
            peices[6][i] = new Pawn(6, i, true);
        }
    }

    Piece get(int row, int col) {
        if (Utils.isOutOfBounds(destRow, destCol)) {
            throw new InvalidPositionException("The destination position of rook is out of bounds.")
        }   
        return pieces[row][col];
    }

    void set(int row, int col, Piece piece) {
        if (Utils.isOutOfBounds(destRow, destCol)) {
            throw new InvalidPositionException("The destination position of rook is out of bounds.")
        } 
        pieces[row][col] = piece;  
    }

    boolean isPositionCheckMate() {
        return false;
    }

    boolean isPositionStaleMate() {
        return false;
    }

    boolean isPositionDraw() {
        return false;
    }


    void display() {
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                if (pieces[i][j] == null) {
                    System.out.print("-");
                } else {
                    if (pieces[i][j] instanceof Rook) {
                        System.out.print("R");
                    } else if (pieces[i][j] instanceof Knight) {
                        System.out.print("H");
                    } else if (pieces[i][j] instanceof Bishop) {
                        System.out.print("B");
                    } else if (pieces[i][j] instanceof King) {
                        System.out.print("K");
                    } else if (pieces[i][j] instanceof Queen) {
                        System.out.print("Q");
                    } else if (pieces[i][j] instanceof Pawn) {
                        System.out.print("P");
                    }
                }
                System.out.println();
            }
        }
    }
}