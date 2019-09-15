package xyz.lius.andy.interpreter.parser;

public interface Parser<T> {
    T parseFile(String fileName);

    T parseString(String expression);
}
