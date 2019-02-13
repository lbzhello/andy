package xyz.lius.andy.expression.runtime;

import xyz.lius.andy.expression.Context;
import xyz.lius.andy.expression.Expression;
import xyz.lius.andy.expression.Name;

public class ReturnExpression implements Expression {
    private Expression retValue;

    public ReturnExpression(Expression retValue) {
        this.retValue = retValue;
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        return retValue;
    }
}