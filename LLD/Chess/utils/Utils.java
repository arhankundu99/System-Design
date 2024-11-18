package utils;
public class Utils {
    public static boolean isOutOfBounds(int row, int col) {
        return row < 0 || row >= 8 || col < 0 || col >= 8;
    }
}