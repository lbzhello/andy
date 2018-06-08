package fun.mandy.expression.support;

import fun.mandy.expression.Expression;

public class ObjectExpression implements Expression {
    private Object object;
    public ObjectExpression(Object object){
        this.object = object;
    }

}
