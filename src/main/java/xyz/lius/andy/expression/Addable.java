package xyz.lius.andy.expression;

public interface Addable<T> extends Expression {
    Addable add(T element);
}
