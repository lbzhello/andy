package xyz.lius.andy.expression.operator;

import xyz.lius.andy.core.OperatorSingleton;
import xyz.lius.andy.expression.*;

public class IfExpression extends AbstractContainer implements Operator {
    public IfExpression() {
        super(2);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        Expression selectExpression;
        if (get(0).eval(context) == ExpressionType.TRUE) {
            selectExpression = get(1) instanceof ElseExpression ? ((ElseExpression) get(1)).get(0) : get(1);
        } else {
            selectExpression = get(1) instanceof ElseExpression ? ((ElseExpression) get(1)).get(1) : ExpressionType.NIL;
        }
        if (ExpressionUtils.isCurlyBracket(selectExpression)) {
            Complex complex = (Complex) selectExpression.eval(context);
            return new StackFrame(complex).eval(null);
        } else {
            return selectExpression.eval(context);
        }
    }

    @Override
    public String toString() {
        return show(OperatorSingleton.IF, super.toString());
    }
}
