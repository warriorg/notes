package datastructures.queue;

/**
 * @author warriorg
 */
public class ArrayQueue<T> implements Queue<T> {
    private final Object[] items;
    private int takeIndex;
    private int putIndex;
    private int count;


    public ArrayQueue(int capacity) {
        this.items = new Object[capacity];
        this.takeIndex = 0;
        this.putIndex = 0;
    }

    private void enqueue(T item) {
        items[putIndex] = item;
        if (++putIndex == items.length) {
            putIndex = 0;
        }
        count++;
    }

    private T dequeue() {
        T t = (T) items[takeIndex];
        items[takeIndex] = null;
        if (++takeIndex == items.length) {
            takeIndex = 0;
        }
        count--;
        return t;
    }

    @Override
    public boolean offer(T elem) {
        if (isFull()) {
            return false;
        } 

        enqueue(elem);
        return true;
    }

    @Override
    public T poll() {
        if (isEmpty()) {
            return null;
        }
        return dequeue();
    }

    @Override
    public T peek() {
       return (T)items[takeIndex];
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public int size() {
        return count;
    }

    public boolean isFull() {
        return count == items.length;
    }

    public static void main(String[] args) {
        ArrayQueue<Integer> queue = new ArrayQueue<>(4);
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        queue.offer(4);     
        System.out.println("size: " + queue.size());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
    }
}

