package xyz.lbzh.andy.expression.ast;

import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.ExpressionType;
import xyz.lbzh.andy.tokenizer.LineNumberToken;

public class ErrorExpression implements Expression {
    private String message;
    private Expression expression;

    private int lineNumber = -1;

    public ErrorExpression(String message) {
        this.expression = ExpressionType.NIL;
        this.message = message;
    }

    public ErrorExpression(Expression expression, String message) {
        this.expression = expression;
        if (expression instanceof LineNumberToken) {
            this.lineNumber = ((LineNumberToken) expression).getLineNumber();
        }
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorExpression in line " + lineNumber + ": " + expression + "\n    " + message;
    }
}
