package xyz.lius.andy.expression.ast;

import xyz.lius.andy.interpreter.tokenizer.LineNumberToken;
import xyz.lius.andy.core.Definition;
import xyz.lius.andy.expression.Context;
import xyz.lius.andy.expression.Expression;
import xyz.lius.andy.expression.Name;

public class ErrorExpression implements Expression {
    private String message;
    private Expression expression;

    private String toSting;

    private int lineNumber = -1;

    public ErrorExpression(String message) {
        this.expression = Definition.NIL;
        this.message = message;
        toSting = "ErrorExpression: " + expression + "\n    " + message;
    }

    public ErrorExpression(Expression expression, String message) {
        this.expression = expression;
        if (expression instanceof LineNumberToken) {
            this.lineNumber = ((LineNumberToken) expression).getLineNumber();
            toSting = "ErrorExpression in line " + lineNumber + ": " + expression + "\n    " + message;
        } else {
            toSting = "ErrorExpression: " + expression + "\n    " + message;
        }
        this.message = message;
    }

    @Override
    public String toString() {
        return toSting;
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        return this;
    }
}
