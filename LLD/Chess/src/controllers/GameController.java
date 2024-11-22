package controllers;

import exceptions.CellOutOfBoundException;
import models.Board;
import models.Color;
import models.Player;
import models.pieces.Pawn;
import models.pieces.Piece;

import java.util.Scanner;

public class GameController {
    public void run() throws CellOutOfBoundException {
        Board board = new Board();

        Piece whitePawn1 = new Pawn(Color.WHITE, 1, 0);

        board.setPiece(1, 0, whitePawn1);

        Player player1 = new Player("Player 1", Color.WHITE);
        Player player2 = new Player("Player 2", Color.WHITE);

        Player currentPlayer = player1;
        Scanner scanner = new Scanner(System.in);
        while (!board.isCheckMatePosition() && !board.isStateMatePosition() && !board.isDrawPosition()) {
            System.out.println("[Player]: " + currentPlayer.getName() + " Please enter the coordinates of the piece you would like to play: ");
            int sourceRow = scanner.nextInt();
            int sourceCol = scanner.nextInt();

            System.out.println("[Player]: " + currentPlayer.getName() + " Please enter the coordinates where you want to move the piece: ");
            int destinationRow = scanner.nextInt();
            int destinationCol = scanner.nextInt();

            Piece piece = board.getPiece(sourceRow, sourceCol);
            try {
                if (board.isCheckPosition()) {
                    while (board.isCheckPosition()) {
                        currentPlayer.move(piece, destinationRow, destinationCol, board);
                        if (!board.isCheckPosition()) {
                            System.out.println("[Player]: " + currentPlayer.getName() + " Please save your king from check: ");
                            currentPlayer.move(piece, sourceRow, sourceCol, board);
                            System.out.println("[Player]: " + currentPlayer.getName() + " Please enter the coordinates where you want to move the piece: ");
                            destinationRow = scanner.nextInt();
                            destinationCol = scanner.nextInt();
                        }
                    }
                }
                else {
                    currentPlayer.move(piece, destinationRow, destinationCol, board);
                }
                if (currentPlayer == player1) {
                    currentPlayer = player2;
                } else {
                    currentPlayer = player1;
                }
                System.out.println("[Player]: " + currentPlayer.getName() + " moved the chosen piece to: " + destinationRow + ", " + destinationCol);

            } catch (Exception e) {
                System.out.println(e.getMessage());

            }
        }
    }
}
