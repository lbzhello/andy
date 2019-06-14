package xyz.lius.andy.expression.base;

import xyz.lius.andy.core.OperatorSingleton;
import xyz.lius.andy.expression.*;

public class NotEqualExpression extends AbstractContainer implements Operator {

    public NotEqualExpression() {
        super(2);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        if (get(0).eval(context).equals(get(1).eval(context))) {
            return ExpressionType.FALSE;
        } else {
            return ExpressionType.TRUE;
        }
    }

    @Override
    public String toString() {
        return show(OperatorSingleton.NOT_EQUAL, super.toString());
    }
}
