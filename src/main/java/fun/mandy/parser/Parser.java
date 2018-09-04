package fun.mandy.parser;

import fun.mandy.expression.Expression;

import java.io.Closeable;
import java.io.Reader;
import java.util.Iterator;

public interface Parser<T> extends Iterator<T>,Closeable {
    T parse(String fileName);
}
