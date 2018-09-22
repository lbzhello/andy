package xyz.lbzh.andy.expression.support;

import xyz.lbzh.andy.expression.Name;

public class NumberExpression extends ValueExpression implements Name {
    public NumberExpression(Object value) {
        this.value = value;
    }

}
