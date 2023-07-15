
import manager.BoardGameManager;

import models.Snake;
import models.Ladder;
import models.Player;

public class Main{
    public static void main(String[] args){
        BoardGameManager boardGameManager = new BoardGameManager(6);

        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

        boardGameManager.addPlayer(player1);
        boardGameManager.addPlayer(player2);

        Snake snake = new Snake(15, 5);
        Ladder ladder = new Ladder(21, 36);

        boardGameManager.addEntity(snake);
        boardGameManager.addEntity(ladder);

        boardGameManager.simulate();
    }
}