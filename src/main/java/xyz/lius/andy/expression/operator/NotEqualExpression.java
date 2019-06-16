package xyz.lius.andy.expression.operator;

import xyz.lius.andy.core.Definition;
import xyz.lius.andy.core.OperatorSingleton;
import xyz.lius.andy.expression.*;
import xyz.lius.andy.util.AbstractContainer;

public class NotEqualExpression extends AbstractContainer implements Operator {

    public NotEqualExpression() {
        super(2);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        if (get(0).eval(context).equals(get(1).eval(context))) {
            return Definition.FALSE;
        } else {
            return Definition.TRUE;
        }
    }

    @Override
    public String toString() {
        return show(OperatorSingleton.NOT_EQUAL, super.toString());
    }
}
