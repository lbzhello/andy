package xyz.lius.andy.expression;

public interface Container<T> {
    void add(T element);

    T get(int i);

    int size();

    boolean isEmpty();

    T[] toArray();

    void addContainer(Container<T> container);
}
