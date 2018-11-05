package xyz.lbzh.andy.expression.runtime;

import xyz.lbzh.andy.expression.Expression;

public class ObjectExpression implements Expression {
    private Object object;

    public ObjectExpression(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
    }
}
