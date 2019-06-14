package xyz.lius.andy.expression;

public interface Container<T> {
    void add(T element);

    T get(int i);

    int count();

    boolean isEmpty();

    T[] toArray();
}
