package manager;
import models.Player;
import manager.DiceManager;
import models.Board;
import models.BoardEntity;
import exceptions.InvalidEntityPositionException;
import java.util.*;

public class BoardGameManager{

    private List<Player> players;
    private int currentPlayerIdx;
    private List<Integer> playerPositions;
    private DiceManager diceManager;
    private Board board;
    private int n;

    public BoardGameManager(int n){
        players = new ArrayList<>();
        currentPlayerIdx = -1;
        playerPositions = new ArrayList<>();
        diceManager = new DiceManager();
        board = new Board(n);
        this.n = n;
    }

    public void addPlayer(Player player){
        players.add(player);
        playerPositions.add(0);
    }

    public void addEntity(BoardEntity entity){
        if(canAddEntity(entity)){
            board.addEntity(entity);
        }
        else throw new InvalidEntityPositionException("Cannot place the entity in the given positions.");
    }

    public int getCurrentPlayerIdx(){
        return currentPlayerIdx;
    }

    public Board getBoard(){
        return board;
    }

    public void simulate(){
        currentPlayerIdx = 0;

        while(players.size() != 0){
            Player currentPlayer = players.get(currentPlayerIdx);

            System.out.println("\n---------------------");
            System.out.println("Current Player is " + currentPlayer.getName());
            int diceNum = diceManager.roll();
            System.out.println("The player rolls " + diceNum);

            movePlayer(board, playerPositions, currentPlayerIdx, playerPositions.get(currentPlayerIdx) + diceNum);
            System.out.println("Current Player moves to " + playerPositions.get(currentPlayerIdx));

            if(playerPositions.get(currentPlayerIdx) == board.getSize()){
                players.remove(currentPlayerIdx);
                playerPositions.remove(currentPlayerIdx);

                System.out.println("Current Player reaches end of board.");
            }
            else{
                currentPlayerIdx = (currentPlayerIdx + 1) % players.size();
            }
        }
        System.out.println("Every player has reached the end of board");
    }

    public void movePlayer(Board board, List<Integer> playerPositions, int currentPlayerIdx, int targetPosition){
        if(targetPosition == board.getSize()){
            playerPositions.set(currentPlayerIdx, targetPosition);
        }

        else if(targetPosition < board.getSize()){
            if(board.getEntityEndPos(targetPosition) != -1){
                playerPositions.set(currentPlayerIdx, board.getEntityEndPos(targetPosition));
            }
            else playerPositions.set(currentPlayerIdx, targetPosition);
        }
    }

    private boolean canAddEntity(BoardEntity boardEntity){

        return boardEntity.getStartPos() > 0 && 
               boardEntity.getEndPos() <= board.getSize() &&
               boardEntity.getStartPos() != boardEntity.getEndPos() && 
               board.getEntityEndPos(boardEntity.getStartPos()) == -1 && 
               board.getEntityEndPos(boardEntity.getEndPos()) == -1;
    }
}