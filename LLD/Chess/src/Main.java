import controllers.GameController;
import exceptions.CellOutOfBoundException;

public class Main {
    public static void main(String[] args) throws CellOutOfBoundException {
        GameController gameController = new GameController();
        gameController.run();
    }
}
