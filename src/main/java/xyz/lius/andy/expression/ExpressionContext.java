package xyz.lius.andy.expression;

public class ExpressionContext extends AbstractContext<Name, Expression> implements Expression {
    public ExpressionContext() {}

    public ExpressionContext(Context<Name, Expression> parent) {
        super(parent);
    }

    @Override
    public Expression lookup(Name key) {
        Expression o = super.lookup(key);
        return o == null ? ExpressionType.NIL : o;
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        return this;
    }
}
