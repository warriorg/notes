package datastructures.queue;

import java.util.Iterator;

import datastructures.linked.DoublyLinkedList;

public class LinkedQueue<T> implements Queue<T>,Iterable<T> {

    private DoublyLinkedList<T> linkedList = new DoublyLinkedList();

    public LinkedQueue() {}
    
    @Override
    public int size() {
        return linkedList.size();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public T peek() {
        return linkedList.peek();
    }

    @Override
    public T poll() {
        return linkedList.removeFirst();
    }
    
    @Override
    public void offer(T item) {
        linkedList.addLast(item);
    }

    @Override
    public Iterator<T> iterator() {
        return linkedList.iterator();
    }
    
}
