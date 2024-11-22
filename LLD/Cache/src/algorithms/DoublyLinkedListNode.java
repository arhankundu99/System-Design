package algorithms;

public class DoublyLinkedListNode<Key, Value> {
    public Key key;
    public Value value;
    public DoublyLinkedListNode<Key, Value> prev;
    public DoublyLinkedListNode<Key, Value> next;

    public DoublyLinkedListNode(Key key, Value value) {
        this.key = key;
        this.value = value;
        prev = null;
        next = null;
    }
}
