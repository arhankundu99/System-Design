package manager;
import models.Dice;
import java.util.*;

public class DiceManager{
    private Dice dice;
    public DiceManager(){
        dice = new Dice(6);
    }

    public int roll(){
        return (new Random()).nextInt(dice.getSize()) + 1;
    }
}