import services.CacheService;
import storage.HashMapStorage;
import storage.IStorage;
import strategies.IEvictionPolicy;
import strategies.LRUEvictionPolicy;
import utils.OutputPrinter;

public class Main {
    public static void main(String[] args) {
        IStorage<Integer, Integer> storage = new HashMapStorage<>(3);
        IEvictionPolicy<Integer, Integer> evictionPolicy = new LRUEvictionPolicy<>();
        OutputPrinter outputPrinter = new OutputPrinter();
        CacheService<Integer, Integer> cacheService = new CacheService<>(storage, evictionPolicy, outputPrinter);

        cacheService.put(1, 1);
        cacheService.put(2, 2);
        cacheService.put(3, 3);

        cacheService.put(4, 4);
        cacheService.put(2, 1);
        cacheService.put(5, 1);
    }
}
