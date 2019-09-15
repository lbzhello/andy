package xyz.lius.andy.interpreter.parser;

import java.util.Iterator;

public interface Parser<T> extends Iterator<T> {
    T parseFile(String fileName);

    T parseString(String expression);

}
