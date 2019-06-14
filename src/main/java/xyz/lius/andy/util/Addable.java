package xyz.lius.andy.util;

public class Addable<T> {
    private static final Object[] EMPTY_ELEMENT_DATA = {};

    private static final int DEFAULT_INITIAL_CAPACITY = 8;

    //元素总数
    protected int count;

    //剩余空间
    protected int free;

    protected Object[] elementData;

    public Addable() {
        elementData = EMPTY_ELEMENT_DATA;
    }

    public Addable(int initialCapacity) {
        free = initialCapacity;
        elementData = new Object[free];
    }

    public void add(T element) {
        if (free == 0) {
            Object[] oldElementData = elementData;
            int len = count == 0 ? DEFAULT_INITIAL_CAPACITY : (count << 1);
            elementData = new Object[len];
            System.arraycopy(oldElementData, 0, elementData, 0, count);
            free = len - count;
        }
        elementData[count] = element;
        count++; free--;
    }

    public T  get(int i) {
        return i < count ? (T) elementData[i] : null;
    }

    public int count() {
        return count;
    }

    public T[] toArray() {
        return (T[]) elementData;
    }
}
