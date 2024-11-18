package controllers;
import java.utils.*;
import models.*;

public class GameController {

    Board board;
    Player player1, player2;
    Player currentPlayer;
    public GameController() {
        board = new Board();
        player1 = new Player(true);
        player2 = new Player(false);

        currentPlayer = player1;
    }

    void run() {
        Scanner scanner = new Scanner(System.in);
        while (!board.isPositionCheckMate() || !board.isPositionStaleMate() || !board.isPositionDraw()) {
            if (currentPlayer == player1) {
                System.out.println("Player 1 turn");
            } else {
                System.out.println("Player 2 turn");
            }

            System.out.print("Enter your chosen piece row and col: ");
            int row = scanner.nextInt();
            int col = scanner.nextInt();

            try {
                Piece chosenPiece = board.get(row, col);
                
                System.out.print("Enter your dest row and dest col: ");
                int destRow = scanner.nextInt();
                int destCol = scanner.nextInt();

                currentPlayer.move(chosenPiece, destRow, destCol, board);

                if (currentPlayer == player1) {
                    currentPlayer = player2;
                } else {
                    currentPlayer = player1;
                }
                board.display();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        if (board.isPositionCheckMate()) {
            if (currentPlayer == player1) {
                System.out.println("Player 2 is the winner!");
            } else {
                System.out.println("Player 1 is the winner!");
            }
        } else {
            System.out.println("The game ended in a draw!");
        }
    }
}