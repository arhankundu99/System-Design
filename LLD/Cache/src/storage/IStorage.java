package storage;

import exceptions.KeyDoesNotExistException;
import exceptions.StorageFullException;

public interface IStorage<Key, Value> {
    void put(Key key, Value value) throws StorageFullException;
    public Value get(Key key) throws KeyDoesNotExistException;
    public void remove(Key key) throws KeyDoesNotExistException;
    public int size();
    public int capacity();
}
