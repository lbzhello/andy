package xyz.lius.andy.expression.base;

import xyz.lius.andy.core.OperatorSingleton;
import xyz.lius.andy.expression.*;

public class ElseExpression extends AbstractContainer implements Operator {
    public ElseExpression() {
        super(2);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        return this;
    }

    @Override
    public Name getName() {
        return ExpressionFactory.symbol(OperatorSingleton.ELSE);
    }

    @Override
    public String toString() {
        return show(OperatorSingleton.ELSE, super.toString());
    }
}
