package xyz.lius.andy.expression.operator;

import xyz.lius.andy.expression.Context;
import xyz.lius.andy.expression.Expression;
import xyz.lius.andy.expression.Name;

/**
 * Represent a java object
 */
public class JavaObject implements Expression {
    private Object object;

    public JavaObject(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    @Override
    public String toString() {
        return String.valueOf(object);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        return this;
    }
}
