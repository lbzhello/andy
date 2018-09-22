package xyz.lbzh.andy.expression.support;

import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.Name;
import xyz.lbzh.andy.expression.Pair;

import java.security.Key;
import java.util.Map;

public class DefaultPair implements Pair<Name,Expression>,Expression {
    private Name key;
    private Expression value;

    public DefaultPair(Name key, Expression value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public Name key() {
        return key;
    }

    @Override
    public Expression value() {
        return value;
    }

    @Override
    public String toString() {
        return this.key.toString() + " -> " + this.value.toString();
    }
}
