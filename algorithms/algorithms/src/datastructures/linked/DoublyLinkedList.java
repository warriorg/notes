package datastructures.linked;

import java.util.Iterator;

/**
 * @author warriorg
 */
public class DoublyLinkedList<T> implements Iterable<T> {
    private int size = 0;
    private Node<T> head = null;
    private Node<T> tail = null;

    class Node<T> {
        T data;
        Node<T> prev;
        Node<T> next;

        Node(T data, Node<T> prev, Node<T> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }

        @Override
        public String toString() {
            return "[" + prev + "|" + data + "|" + next + "]";
        } 
    }

    public T peek() {
        return head == null ? null : head.data;
    }


    public Boolean add(T item) {
        addLast(item); 
        return true; 
    }

    public void addFirst(T item) {
        final Node<T> first = head;
        final Node<T> node = new Node<>(item, null, head);
        head = node;
        if (first == null) {
            tail = node;
        } else {
            first.prev = node;
        }
        size++;
    }

    public void addLast(T item) {
        final Node<T> prev = tail; 
        final Node<T> node = new Node<>(item, prev, null);
        tail = node;
        if (prev == null) {
            head = node;
        } else {
            prev.next = node;
        }
        size++;
    }

    public void clear() {
        for (Node<T> curr = head; curr != null;) {
            Node<T> next = curr.next;
            curr.data = null;
            curr.prev = null;
            curr.next = null;
            curr = next;
        }

        head = tail = null;
        size = 0;
    }

    public T get(int index) {
        checkElementIndex(index);

        Node<T> curr = head;
        for (int i = 0; i < size; i++) {
            if (index == i) {
                return curr.data;
            }
            curr = curr.next;            
        }
        return null;
    }

    /** Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list. */
    public void addAtHead(int val) {

    }

    /** Append a node of value val to the last element of the linked list. */
    public void addAtTail(int val) {

    }

    /** Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted. */
    public void addAtIndex(int index, int val) {

    }

    /** Delete the index-th node in the linked list, if the index is valid. */
    public void deleteAtIndex(int index) {

    }

    public void remove(T item) {
        Node<T> curr = findNode(item);
        if (curr == null) {
            return;
        }
        if (curr.prev != null) {
            curr.prev.next = curr.next;
        }
        if (curr.next != null) {
            curr.next.prev = curr.prev;
        }
        return true;
    }

    public T removeFirst() {
        if (head != null) {
            head = head.next;
            if (head != null) {
                head.prev = null;
                return head.data;
            } else {
                head = tail = null;
            }
        }
        return null;
    }

    public T removeLast() {
        if (tail == null) {
            return null;
        } 
        if (tail != null) {
            tail.prev = tail.prev;
            tail.next = null;
            return tail.data;
        } else {
            head = tail = null;
        }
        return null;
    }

    public int size() {
        return size;
    }

    private Node<T> findNode(T item) {
        if (item == null) {
            return null;
        }
        Node<T> curr = head;
        if (curr != null && item.equals(curr.data)) {
            return curr;
        }
        return null;
    }

    private void checkElementIndex(int index) {
        if (!(index > 0 && index < size)) {
           throw new IndexOutOfBoundsException(); 
        }
    }


    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> curr = head;

            @Override
            public boolean hasNext() {
                return curr != null;
            }

            @Override
            public T next() {
                T data = curr.data;
                curr = curr.next;
                return data;
            }

            @Override
            public void remove() {
            }
        };
    }
}
