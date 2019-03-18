package xyz.lius.andy.compiler.parser;

public interface Parser<T> {
    T parseFile(String fileName);

    T parseString(String expression);
}
