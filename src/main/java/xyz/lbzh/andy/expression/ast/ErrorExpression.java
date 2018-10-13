package xyz.lbzh.andy.expression.ast;

import xyz.lbzh.andy.expression.ExpressionType;
import xyz.lbzh.andy.expression.runtime.ReturnExpression;

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
