package datastructures.linked;

import java.util.Iterator;

public class SinglyLinkedList<T> implements Iterable<T> {
    private int size = 0;
    private Node<T> head = null;
    private Node<T> tail = null;

    class Node<T> {
        private T data;
        private Node<T> next;

        public Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }

        @Override
        public String toString() {
            return new StringBuilder("data:").append(data).append(" next:").append(next).toString();
        }
    }

    boolean isEmpty() {
        return size == 0;
    }

    Node<T> add(T item) {
        return addLast(item);
    }

    Node<T> get(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        
        Node<T> temp = head;
        for (int i = 0; i < size; i++) {
            temp = temp.next;
        }
        return temp;
    }

    Node<T> find(T item) {
        Node<T> curr = head;
        while (curr != null && !curr.data.equals(item)) {
            curr = curr.next;
        }
        return curr; 
    }

    Node<T> findPrev(T item) {
        Node<T> curr = head;
        while (curr != null && curr.next != null && !curr.next.data.equals(item)) {
            curr = curr.next; 
        }
        return curr;
    }

    Node<T> addFirst(T item) {
        Node<T> node = new Node<>(item, null);
        if (this.isEmpty()) {
            head = tail = node;
        } else {
            node.next = head;
            head = node;
        }
        size++;
        return node;
    }

    Node<T> addLast(T item) {
        Node<T> node = new Node<>(item, null);
        if (this.isEmpty()) {
            head = tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
        return node;

    }
    

    void remove(T item) {
        Node<T> prev = findPrev(item);
        if (prev != null && prev.next != null) {
           prev.next = prev.next.next; 
        } else {
            head = null;
        }
        size--;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> curr = head;

            public boolean hasNext() {
                return curr != null;
            }

            public T next() {
                T data = curr.data;
                curr = curr.next;
                return data;
            }

            public void remove() {
                if (curr == null) {
                    return;
                }
            }
            
        };
    }

    @Override
    public String toString() {
        Iterator<T> it = iterator();
        if (!it.hasNext()) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (;;) {
            T item = it.next();
            sb.append(item);
            if (!it.hasNext()) {
                sb.append(']').toString();
                break;
            }
            sb.append(',').append(' ');
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        SinglyLinkedList<Integer> linkedList = new SinglyLinkedList();
        linkedList.add(1);
        linkedList.add(1);
        linkedList.add(1);
        linkedList.remove(1);
        System.out.println(linkedList);
    }
}
