package services;

import exceptions.KeyDoesNotExistException;
import exceptions.StorageFullException;
import storage.IStorage;
import strategies.IEvictionPolicy;
import utils.OutputPrinter;

public class CacheService<Key, Value> {
    IStorage<Key, Value> storage;
    IEvictionPolicy<Key, Value> evictionPolicy;
    OutputPrinter outputPrinter;
    public CacheService(IStorage<Key, Value> storage, IEvictionPolicy<Key, Value> evictionPolicy, OutputPrinter outputPrinter) {
        this.storage = storage;
        this.evictionPolicy = evictionPolicy;
        this.outputPrinter = outputPrinter;
    }

    public void put(Key key, Value value) {
        try {
            storage.put(key, value);
            evictionPolicy.keyAccessed(key, value);
            outputPrinter.printWithNewLine("Added key:" + key + " with value: " + value);
        } catch (StorageFullException e) {
            outputPrinter.printWithNewLine("Storage is full.");
            Key keyToBeRemoved = evictionPolicy.getKeyToEvict();
            outputPrinter.printWithNewLine("Key to be removed from storage: " + keyToBeRemoved.toString());
            try {
                storage.remove(keyToBeRemoved);
                evictionPolicy.evict(keyToBeRemoved);
                outputPrinter.printWithNewLine("Removed key: " + keyToBeRemoved.toString());

                storage.put(key, value);
                evictionPolicy.keyAccessed(key, value);
                outputPrinter.printWithNewLine("Added key:" + key + " with value: " + value);
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
}
