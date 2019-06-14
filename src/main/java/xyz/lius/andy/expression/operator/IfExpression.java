package xyz.lius.andy.expression.operator;

import xyz.lius.andy.core.OperatorSingleton;
import xyz.lius.andy.expression.*;
import xyz.lius.andy.expression.context.ExpressionContext;

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
            return selectExpression.eval(context).eval(new ExpressionContext());
        } else {
            return selectExpression.eval(context);
        }
    }

    @Override
    public String toString() {
        return show(OperatorSingleton.IF, super.toString());
    }
}
