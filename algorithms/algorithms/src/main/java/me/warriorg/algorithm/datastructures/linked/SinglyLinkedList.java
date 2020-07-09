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
            return data.toString();
        }
    }

    void add(T item) {

    }

    Node<T> get(int index) {
        return null;
    }

    void addFirst(T item) {

    }

    void addLast(T item) {

    }
    

    void remove(T item) {

    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }
}
