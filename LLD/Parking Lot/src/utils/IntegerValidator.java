package utils;

public class IntegerValidator {
    public static boolean isInteger(String param) {
        try {
            Integer.parseInt(param);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
