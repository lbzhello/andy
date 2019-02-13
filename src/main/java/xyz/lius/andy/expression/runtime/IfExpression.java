package xyz.lius.andy.expression.runtime;

import xyz.lius.andy.expression.*;
import xyz.lius.andy.expression.ast.BracketExpression;

import java.util.List;

public class IfExpression extends NativeExpression {
    @Override
    public Expression parameters(List<Expression> list) {
        return new IfExpression().list(list);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        Expression selectExpression;
        if (first().eval(context) == ExpressionType.TRUE) {
            selectExpression = second().getName().toString().equals("else") ? ((BracketExpression) second()).second() : second();
        } else {
            selectExpression = second().getName().toString().equals("else") ? ((BracketExpression) second()).third() : ExpressionType.NIL;
        }
        if (ExpressionUtils.isCurlyBracket(selectExpression)) {
            return selectExpression.eval(context).eval(new ExpressionContext());
        } else {
            return selectExpression.eval(context);
        }
    }
}
