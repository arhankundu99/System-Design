package models;

public class Dice {
    private final int maxValue;
    public Dice(int maxValue) {
        this.maxValue = maxValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public int roll() {
        return (int)((Math.random() * maxValue) + 1);
    }

}
