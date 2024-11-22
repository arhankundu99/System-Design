package storage;

import exceptions.KeyDoesNotExistException;
import exceptions.StorageFullException;

import java.util.HashMap;
import java.util.Map;

public class HashMapStorage<Key, Value> implements IStorage<Key, Value> {
    public Map<Key, Value> storage;
    int capacity;
    public HashMapStorage(int capacity) {
        this.capacity = capacity;
        storage = new HashMap<>();
    }
    public void put(Key key, Value value) throws StorageFullException {
        if (storage.containsKey(key) || storage.size() < capacity) {
            storage.put(key, value);
        } else if (storage.size() == capacity) {
            throw new StorageFullException("Cache is full now. Cannot insert a new key.");
        }
    }

    public Value get(Key key) throws KeyDoesNotExistException {
        if (storage.containsKey(key)) {
            return storage.get(key);
        }

        throw new KeyDoesNotExistException("The given key: " + key + " does not exist in cache. Cannot get.");
    }

    public void remove(Key key) throws KeyDoesNotExistException {
        if (storage.containsKey(key)) {
            storage.remove(key);
        } else {
            throw new KeyDoesNotExistException("The given key: " + key + " does not exist in cache. Cannot remove.");
        }
    }

    public int size() {
        return storage.size();
    }

    public int capacity() {
        return capacity;
    }
}
