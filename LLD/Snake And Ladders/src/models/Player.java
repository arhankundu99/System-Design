package models;

public class Player {
    private final String name;
    private final Dice dice;
    int currentPosition;
    public Player(String name, Dice dice) {
        this.name = name;
        this.currentPosition = 0;
        this.dice = dice;
    }

    public String getName() {
        return name;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public int rollDice() {
        return dice.roll();
    }

}
