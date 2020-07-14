package datastructures.array;

import java.util.Iterator;

public class DynamicArray<T> implements Iterable<T>{

    /***
     * Default initial capacity
     * */
    private static final int DEFAULT_CAPACITY = 8;

    /**
     * the array buffer into which the elements of the DynamicArray are stored
     * */
    private transient T[] elementData;

    /**
     * 
     * */
    private int size = 0; 

    /***
     *
     * */
    private int capacity = 0;

    public DynamicArray() {
        this(DEFAULT_CAPACITY);
    }  

    public DynamicArray(int capacity) {
        if (capacity < 0) throw new IllegalArgumentException("Illegal capacity:" + capacity);
        this.capacity = capacity;
        elementData = (T []) new Object[capacity];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public T get(int index) {
        rangeCheck(index);
        return elementData[index];
    }

    public void add(T item) {
        ensureCapacityInternal(size + 1);
        elementData[size++] = item;
    }

    public T removeAt(int index) {
        rangeCheck(index);
        T data = elementData[index];
        for (int i = index; i < size - 1; i++ ) {
           elementData[i] = elementData[i + 1];
        }        
        elementData[--size] = null;
        return data;
    }

    public int indexOf(T item) {
        for (int i = 0; i < size; i++) {
            if (item == null) {
                if (elementData[i] == null) {
                    return i;
                }
            } else {
                if (item.equals(elementData[i])) {
                    return i;
                }
            }
        }

        return -1;
    }


    private void rangeCheck(int index) {
        if (index >= size() || index < 0) {
            throw new IndexOutOfBoundsException();
        } 
    }

    public void ensureCapacityInternal(int minCapacity) {
        if (minCapacity < capacity) {
            return;
        }    
        if (capacity == 0) {
            capacity = DEFAULT_CAPACITY;
        }
        capacity *= 2;
        T[] newElement = (T []) new Object[capacity];
        for (int i = 0; i < size; i++) {
            newElement[i] = elementData[i];
        } 
        elementData = newElement;
    }

    @Override
    public java.util.Iterator<T> iterator() {
        return new Iterator<T>() {
            int cursor = 0;

            public boolean hasNext() {
                return cursor < size;
            }

            public T next() {
                int i = cursor;

                cursor++;
                return elementData[i];
            }

            public void remove() {
                
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
        sb.append("capacity:").append(this.capacity + "");
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("dynamic array");
        DynamicArray<Integer> intArr = new DynamicArray(0);
        intArr.add(3);
        intArr.add(6);
        intArr.add(2);
        intArr.add(-2);
        intArr.add(7);
        System.out.println(intArr);

        intArr.add(7);
        intArr.add(3);
        intArr.add(3);
        intArr.add(6);
        intArr.add(3);
        intArr.add(3);
        intArr.add(6);
        intArr.add(2);
        intArr.add(-2);
        intArr.add(6);
        intArr.add(2);
        intArr.add(-2);

        
        System.out.println(intArr);
        System.out.print("删除index 2");
        intArr.removeAt(2);
        System.out.println(intArr);        
    }
}
