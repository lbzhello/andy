package xyz.lius.andy.compiler.tokenizer;

import xyz.lius.andy.io.CharIterator;

public interface Tokenizer<T> {

    void setResource(CharIterator iterator);

    T current();

    T next();

    boolean hasNext();

    default void reset() {}

    default int getLineNumber() {
        return 0;
    }
}
