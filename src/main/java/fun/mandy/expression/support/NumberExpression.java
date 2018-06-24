package fun.mandy.expression.support;

import fun.mandy.expression.Context;
import fun.mandy.expression.Expression;
import fun.mandy.expression.Name;

public class NumberExpression extends ObjectExpression implements Name {

    public NumberExpression(){}

    public NumberExpression(Object value) {
        this.value = value;
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        return new NumberExpression(this.value);
    }
}
