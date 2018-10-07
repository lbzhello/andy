package xyz.lbzh.andy.expression.internal;

import xyz.lbzh.andy.expression.ExpressionType;

public class ErrorExpression extends ReturnExpression {
    private String message;

    public ErrorExpression(String message) {
        super(ExpressionType.NIL);
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorExpression: " + message;
    }
}
