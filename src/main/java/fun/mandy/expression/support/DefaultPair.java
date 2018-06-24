package fun.mandy.expression.support;

import fun.mandy.expression.Expression;
import fun.mandy.expression.Name;
import fun.mandy.expression.Pair;

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
}
