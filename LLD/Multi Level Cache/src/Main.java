import services.MultiLevelCacheService;
import utils.OutputPrinter;

public class Main {
    public static void main(String[] args) {
        int numLevels = 5;
        int[] readTimes = new int[]{1, 2, 3, 4, 5};
        int[] writeTimes = new int[]{1, 2, 3, 4, 5};
        int[] capacity = new int[]{1, 2, 3, 4, 5};
        OutputPrinter outputPrinter = new OutputPrinter();
        MultiLevelCacheService<Integer, Integer> multiLevelCacheService = new MultiLevelCacheService<>(numLevels, readTimes, writeTimes, capacity, outputPrinter);

        multiLevelCacheService.put(1, 1);
        multiLevelCacheService.put(2, 2);
    }
}
