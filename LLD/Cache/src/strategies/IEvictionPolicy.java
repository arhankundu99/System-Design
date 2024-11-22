package strategies;

import exceptions.KeyDoesNotExistException;

public interface IEvictionPolicy<Key, Value> {
    void keyAccessed(Key key, Value value);

    Key getKeyToEvict();

    void evict(Key key) throws KeyDoesNotExistException;
}
