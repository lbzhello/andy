package fun.mandy.tokenizer;

public interface Token<T,V> {
    T type();
    V value();
}
