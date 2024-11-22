package algorithms;

public class DoublyLinkedList<Key, Value> {
    DoublyLinkedListNode<Key, Value> head;
    DoublyLinkedListNode<Key, Value> tail;

    public void addToFirst(DoublyLinkedListNode<Key, Value> node) {
        if (head == null) {
            head = node;
            tail = node;
        } else {
            node.next = head;
            head.prev = node;
            head = node;
        }
    }

    public void removeNode(DoublyLinkedListNode<Key, Value> node) {
        if (head == node) {
            head = head.next;

            if (head == null) {
                tail = null;
            } else {
                head.prev.next = null;
                head.prev = null;
            }
        } else if (tail == node) {
            tail = tail.prev;
            if (tail == null) {
                head = null;
            } else {
                tail.next.prev = null;
                tail.next = null;
            }
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    public DoublyLinkedListNode<Key, Value> getHead() {
        return head;
    }

    public DoublyLinkedListNode<Key, Value> getTail() {
        return tail;
    }
}
