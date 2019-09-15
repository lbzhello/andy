package xyz.lius.andy.expression.operator;

import xyz.lius.andy.core.OperatorSingleton;
import xyz.lius.andy.expression.*;
import xyz.lius.andy.util.AbstractContainer;
import xyz.lius.andy.util.Pair;

/**
 * e.g. left = "hello"
 */
public class AssignExpression extends AbstractContainer implements Operator {

    public AssignExpression() {
        super(2);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        return ExpressionUtils.evalAssign(context, new Pair<>(get(0), get(1)), false);
    }

    @Override
    public String toString() {
        return show(OperatorSingleton.ASSIGN, super.toString());
    }
}
