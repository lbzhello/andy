package fun.mandy.expression;

public interface Node<T> {
    T getParent();

    void setParent(T parent);
}
