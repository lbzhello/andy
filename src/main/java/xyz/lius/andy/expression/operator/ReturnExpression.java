package xyz.lius.andy.expression.operator;

import xyz.lius.andy.core.OperatorSingleton;
import xyz.lius.andy.expression.*;
import xyz.lius.andy.util.AbstractContainer;

public class ReturnExpression extends AbstractContainer implements Operator {
    public ReturnExpression() {
        super(1);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        return new ReturnValue(get(0).eval(context));
    }

    @Override
    public String toString() {
        return show(OperatorSingleton.RETURN, super.toString());
    }
}
