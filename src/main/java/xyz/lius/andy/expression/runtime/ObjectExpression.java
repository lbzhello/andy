package xyz.lius.andy.expression.runtime;

import xyz.lius.andy.expression.Expression;

public class ObjectExpression implements Expression {
    private Object object;

    public ObjectExpression(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    @Override
    public String toString() {
        return String.valueOf(object);
    }
}
