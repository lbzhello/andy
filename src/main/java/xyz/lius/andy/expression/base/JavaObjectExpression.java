package xyz.lius.andy.expression.base;

import xyz.lius.andy.expression.Context;
import xyz.lius.andy.expression.Expression;
import xyz.lius.andy.expression.Name;

/**
 * 对象表达式，代表任一个java对象
 */
public class JavaObjectExpression implements Expression {
    private Object object;

    public JavaObjectExpression(Object object) {
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
