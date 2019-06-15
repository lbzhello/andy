package xyz.lius.andy.expression;

public class ReturnValue implements Expression {
    private Expression value;

    public ReturnValue(Expression value) {
        this.value = value;
    }

    public Expression getValue() {
        return value;
    }

    public void setValue(Expression value) {
        this.value = value;
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        return this;
    }
}
