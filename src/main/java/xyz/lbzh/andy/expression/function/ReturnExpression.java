package xyz.lbzh.andy.expression.function;

import xyz.lbzh.andy.expression.Expression;

public class ReturnExpression implements Expression {
    private Expression retValue;

    public ReturnExpression(Expression retValue) {
        this.retValue = retValue;
    }

    public Expression get() {
        return this.retValue;
    }
}
