package fun.mandy.parser;

import fun.mandy.expression.Expression;

import java.io.Reader;

public interface Parser<T> {
    T generate();
    void init(Reader reader);
    void close();
}
