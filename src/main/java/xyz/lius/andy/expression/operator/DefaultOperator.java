package xyz.lius.andy.expression.operator;

import xyz.lius.andy.expression.*;
import xyz.lius.andy.util.AbstractContainer;

public class DefaultOperator extends AbstractContainer implements Operator {
    @Override
    public Expression eval(Context<Name, Expression> context) {
        return this;
    }

    @Override
    public String toString() {
        return show("operator", super.toString());
    }
}
