package xyz.lius.andy.expression.operator;

import xyz.lius.andy.core.OperatorSingleton;
import xyz.lius.andy.expression.*;
import xyz.lius.andy.util.AbstractContainer;
import xyz.lius.andy.util.Pair;

public class LetExpression extends AbstractContainer implements Operator {
    public LetExpression() {
        super(1);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        AssignExpression assign = (AssignExpression) get(0);

        return ExpressionUtils.evalAssign(context, new Pair<>(assign.get(0), assign.get(1)), true);
    }

    @Override
    public String toString() {
        return show(OperatorSingleton.LET, super.toString());
    }
}
