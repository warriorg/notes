package datastructures.queue;


public interface Queue<T> {

    public void offer(T elem);

    public T poll();

    public T peek();

    public int size();

    public boolean isEmpty();
}
