package xyz.lius.andy.util;

import xyz.lius.andy.expression.Expression;

public interface Container<T> {
    void add(T element);

    T get(int i);

    int size();

    boolean isEmpty();

    T[] toArray();

    void add(Expression[] array);

    void addAll(Container<T> container);
}
