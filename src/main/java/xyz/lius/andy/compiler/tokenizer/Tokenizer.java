package xyz.lius.andy.compiler.tokenizer;

import xyz.lius.andy.io.CharIterator;

public interface Tokenizer<T> {

    void setResource(CharIterator iter);

    T current();

    T next();

    boolean hasNext();

    default int getLineNumber() {
        return 0;
    }
}
