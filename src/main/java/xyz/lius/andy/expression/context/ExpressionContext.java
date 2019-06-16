package xyz.lius.andy.expression.context;

import xyz.lius.andy.core.Definition;
import xyz.lius.andy.expression.Context;
import xyz.lius.andy.expression.Expression;
import xyz.lius.andy.expression.Name;

public class ExpressionContext extends AbstractContext<Name, Expression> implements Expression {
    public ExpressionContext() {}

    public ExpressionContext(Context<Name, Expression> parent) {
        super(parent);
    }

    @Override
    public Expression lookup(Name key) {
        Expression o = super.lookup(key);
        return o == null ? Definition.NIL : o;
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        return this;
    }
}
