package xyz.lius.andy.interpreter.tokenizer;

public interface Tokenizer<T> {

    T current();

    T next();

    boolean hasNext();

    default void reset() {}

    default int getLineNumber() {
        return 0;
    }
}
