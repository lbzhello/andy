package xyz.lius.andy.expression.operator;

import xyz.lius.andy.core.Definition;
import xyz.lius.andy.core.OperatorSingleton;
import xyz.lius.andy.expression.*;

public class EqualExpression extends AbstractContainer implements Operator {
    public EqualExpression() {
        super(2);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        if (get(0).eval(context).equals(get(1).eval(context))) {
            return Definition.TRUE;
        } else {
            return Definition.FALSE;
        }
    }

    @Override
    public String toString() {
        return show(OperatorSingleton.EQUAL, super.toString());
    }
}
