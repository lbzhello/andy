package xyz.lius.andy.expression.operator;

import xyz.lius.andy.core.OperatorSingleton;
import xyz.lius.andy.expression.*;
import xyz.lius.andy.util.AbstractContainer;

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
