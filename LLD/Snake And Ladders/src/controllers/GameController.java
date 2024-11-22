package controllers;

import exceptions.InvalidEntityPositionException;
import models.*;

public class GameController {
    private Board board;
    private final int BOARD_SIZE = 100;
    public GameController() {
        this.board = new Board(BOARD_SIZE);
    }

    private void placeEntities() throws InvalidEntityPositionException {
        this.board.placeEntity(new Snake(55, 44));

        this.board.placeEntity(new Ladder(20, 23));
    }

    public Board getBoard() {
        return board;
    }

    public void run() throws InterruptedException {
        Dice dice = new Dice(6);
        Player player1 = new Player("Player 1", dice);
        Player player2 = new Player("Player 2", dice);

        Player currentPlayer = player1;
        while (true) {
            System.out.println(currentPlayer.getName() + "'s turn!");
            System.out.println("Rolling dice...");
            int diceNum = currentPlayer.rollDice();
            System.out.println("Dice value: " + diceNum);
            int currentPosition = currentPlayer.getCurrentPosition();
            System.out.println("Current position: " + currentPosition);

            int nextPosition = diceNum + currentPosition;
            if (nextPosition == board.getSize()) {
                System.out.println(currentPlayer.getName() + " is the winner!");
                break;
            } else if (nextPosition > board.getSize()) {
                System.out.println(currentPlayer.getName() + " cannot move as the next position is greater than the board size!");
            } else {
                Entity entity = board.getEntity(nextPosition);
                if (entity != null) {
                    nextPosition = entity.getEnd();
                    System.out.println(currentPlayer.getName() + " encountered a entity");
                }
                System.out.println("Next position: " + nextPosition);
                currentPlayer.setCurrentPosition(nextPosition);
            }

            if (currentPlayer == player1) {
                currentPlayer = player2;
            } else {
                currentPlayer = player1;
            }

            Thread.sleep(1000);
        }
    }
}
