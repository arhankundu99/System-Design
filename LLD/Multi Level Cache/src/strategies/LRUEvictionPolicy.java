package strategies;

import algorithms.DoublyLinkedList;
import algorithms.DoublyLinkedListNode;
import exceptions.KeyDoesNotExistException;

import java.util.HashMap;
import java.util.Map;

public class LRUEvictionPolicy<Key, Value> implements IEvictionPolicy<Key, Value> {
    Map<Key, DoublyLinkedListNode<Key, Value>> map;
    DoublyLinkedList<Key, Value> list;
    public LRUEvictionPolicy() {
        map = new HashMap<>();
        list = new DoublyLinkedList<>();
    }
    public void keyAccessed(Key key, Value value) {
        DoublyLinkedListNode<Key, Value> node = map.get(key);
        if (node == null) {
            node = new DoublyLinkedListNode<Key, Value>(key, value);
            map.put(key, node);
        } else {
            list.removeNode(node);
        }
        list.addToFirst(node);
        node.value = value;
    }

    public Key getKeyToEvict() {
        return list.getTail().key;
    }

    public void evict(Key key) throws KeyDoesNotExistException {
        DoublyLinkedListNode<Key, Value> node = map.get(key);
        if (node == null) {
            throw new KeyDoesNotExistException("Key does not exist. Cannot evict using eviction policy");
        }

        list.removeNode(node);
        map.remove(key);
    }
}
