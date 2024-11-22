package services;

import models.CacheReadResponse;
import models.CacheWriteResponse;
import storage.HashMapStorage;
import strategies.LRUEvictionPolicy;
import utils.OutputPrinter;

import java.util.HashMap;
import java.util.Map;

public class MultiLevelCacheService <Key, Value> {
    Map<Integer, SingleLevelCacheService<Key, Value>> multiLevelCacheMap;
    int numLevels;
    public MultiLevelCacheService(int numLevels, int[] readTimes, int[] writeTimes, int[] capacity, OutputPrinter outputPrinter) {
        multiLevelCacheMap = new HashMap<Integer, SingleLevelCacheService<Key, Value>>();
        for (int i = 1; i <= numLevels; i++) {
            HashMapStorage<Key, Value> mapStorage = new HashMapStorage<>(capacity[i - 1]);
            LRUEvictionPolicy<Key, Value> evictionPolicy = new LRUEvictionPolicy<>();
            multiLevelCacheMap.put(i, new SingleLevelCacheService<Key, Value>(mapStorage, evictionPolicy, outputPrinter, i, readTimes[i - 1], writeTimes[i - 1]));
        }
        this.numLevels = numLevels;
    }

    public CacheReadResponse<Value> getKey(Key key) {
        int lastLevelWhereKeyIsFound = -1;
        Value foundValue = null;
        int timeTaken = 0;

        for (int level = 1; level <= numLevels; level++) {
            SingleLevelCacheService<Key, Value> singleLevelCacheService = multiLevelCacheMap.get(level);
            Value value = singleLevelCacheService.get(key);
            timeTaken += singleLevelCacheService.getReadTime();
            if (value != null) {
                lastLevelWhereKeyIsFound = level;
                foundValue = value;
                break;
            }
        }

        for (int level = 1; level <= lastLevelWhereKeyIsFound; level++) {
            SingleLevelCacheService<Key, Value> singleLevelCacheService = multiLevelCacheMap.get(level);
            singleLevelCacheService.put(key, foundValue);
            timeTaken += singleLevelCacheService.getWriteTime();
        }
        return new CacheReadResponse<Value>(foundValue, timeTaken);
    }

    public CacheWriteResponse put(Key key, Value value) {
        int timeTaken = 0;
        for (int level = 1; level <= numLevels; level++) {
            SingleLevelCacheService<Key, Value> singleLevelCacheService = multiLevelCacheMap.get(level);
            Value foundValue = singleLevelCacheService.get(key);
            timeTaken += singleLevelCacheService.getReadTime();
            singleLevelCacheService.put(key, value);
            timeTaken += singleLevelCacheService.getWriteTime();
            if (foundValue == value) {
                break;
            }
        }
        return new CacheWriteResponse(timeTaken);
    }
}
