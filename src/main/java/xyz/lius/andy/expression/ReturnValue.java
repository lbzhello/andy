package xyz.lius.andy.expression;

/**
 * 返回值表达式，计算流遇到它会直接返回，不会计算下面的表达式
 * engine will return and not eval the rest expressions when encounter a return value
 */
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
