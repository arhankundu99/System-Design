package strategies;

import exceptions.KeyDoesNotExistException;

public class LFUEvictionPolicy<Key, Value> implements IEvictionPolicy<Key, Value> {
    public void keyAccessed(Key key, Value value) {

    }

    public Key getKeyToEvict() {
        return null;
    }

    public void evict(Key key) throws KeyDoesNotExistException {

    }
}
