package xyz.lbzh.andy.parser;

public interface Parser<T> {
    T parseFile(String fileName);

    T parseString(String expression);
}
