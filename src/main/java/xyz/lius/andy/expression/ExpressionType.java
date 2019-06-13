package xyz.lius.andy.expression;

public enum ExpressionType implements Addable<Expression> {
    NIL, DEFINE, PAIR, LAMBDA, PARENT,
    PLUS, MINUS, MULTIPLY, DIVIDE,
    OR, AND, NOT,
    TRUE, FALSE,
    PRINT,
    ARRAY,;

    @Override
    public Expression eval(Context<Name, Expression> context) {
        return this;
    }

    @Override
    public Addable add(Expression element) {
        return this;
    }
}
