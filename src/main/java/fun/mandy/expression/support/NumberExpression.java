package fun.mandy.expression.support;

import fun.mandy.expression.Name;

public class NumberExpression extends ValueExpression implements Name {
    public NumberExpression(Object value) {
        this.value = value;
    }

}
