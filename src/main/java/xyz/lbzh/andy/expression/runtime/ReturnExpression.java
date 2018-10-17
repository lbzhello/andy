package xyz.lbzh.andy.expression.runtime;

import xyz.lbzh.andy.expression.Context;
import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.Name;

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
