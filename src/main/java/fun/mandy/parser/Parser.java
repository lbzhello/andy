package fun.mandy.parser;

import fun.mandy.expression.Expression;

public interface Parser<T> {
    T generate();
}
