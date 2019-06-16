package xyz.lius.andy.util;

public interface Container<T> {
    void add(T element);

    T get(int i);

    int size();

    boolean isEmpty();

    T[] toArray();

    void addContainer(Container<T> container);
}
