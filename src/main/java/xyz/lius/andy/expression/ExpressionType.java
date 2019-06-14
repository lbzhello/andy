package xyz.lius.andy.expression;

public enum ExpressionType implements Operator {
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
    public void add(Expression element) {}
}
