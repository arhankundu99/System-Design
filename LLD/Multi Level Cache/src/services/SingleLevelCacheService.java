package services;

import exceptions.KeyDoesNotExistException;
import exceptions.StorageFullException;
import storage.IStorage;
import strategies.IEvictionPolicy;
import utils.OutputPrinter;

public class SingleLevelCacheService<Key, Value> {
    IStorage<Key, Value> storage;
    IEvictionPolicy<Key, Value> evictionPolicy;
    OutputPrinter outputPrinter;
    int level;
    int readTime;
    int writeTime;
    public SingleLevelCacheService(IStorage<Key, Value> storage, IEvictionPolicy<Key, Value> evictionPolicy, OutputPrinter outputPrinter, int level, int readTime, int writeTime) {
        this.storage = storage;
        this.evictionPolicy = evictionPolicy;
        this.outputPrinter = outputPrinter;
        this.level = level;
        this.readTime = readTime;
        this.writeTime = writeTime;
    }

    public void put(Key key, Value value) {
        try {
            storage.put(key, value);
            evictionPolicy.keyAccessed(key, value);
            outputPrinter.printWithNewLine("Added key:" + key + " with value: " + value + " at level " + level);
        } catch (StorageFullException e) {
            outputPrinter.printWithNewLine("Storage is full.");
            Key keyToBeRemoved = evictionPolicy.getKeyToEvict();
            outputPrinter.printWithNewLine("Key to be removed from storage: " + keyToBeRemoved.toString() + " at level " + level);
            try {
                storage.remove(keyToBeRemoved);
                evictionPolicy.evict(keyToBeRemoved);
                outputPrinter.printWithNewLine("Removed key: " + keyToBeRemoved.toString() + " at level " + level);

                storage.put(key, value);
                evictionPolicy.keyAccessed(key, value);
                outputPrinter.printWithNewLine("Added key:" + key + " with value: " + value + " at level " + level);
            } catch (Exception e1) {
                System.out.println("Error: " + e1.getMessage());
            }

        }
    }

    public Value get(Key key){
        try {
            Value value = storage.get(key);
            evictionPolicy.keyAccessed(key, value);
            return value;
        } catch (KeyDoesNotExistException e) {
            return null;
        }
    }

    public int getLevel() {
        return level;
    }

    public int getReadTime() {
        return readTime;
    }

    public int getWriteTime() {
        return writeTime;
    }
}
