package xyz.lbzh.andy.parser;

import xyz.lbzh.andy.expression.Expression;

import java.io.Closeable;
import java.io.Reader;
import java.util.Iterator;

public interface Parser<T> extends Iterator<T>,Closeable {
    T parseFile(String fileName);

    T parseString(String expression);
}
