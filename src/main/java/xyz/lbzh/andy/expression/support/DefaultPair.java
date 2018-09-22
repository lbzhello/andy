package xyz.lbzh.andy.expression.support;

import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.Pair;

public class DefaultPair implements Pair<Expression, Object>,Expression {
    private Expression key;
    private Object value;

    public DefaultPair(Expression key, Object value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public Expression key() {
        return key;
    }

    @Override
    public Object value() {
        return value;
    }

    @Override
    public String toString() {
        return this.key.toString() + " -> " + this.value.toString();
    }
}
