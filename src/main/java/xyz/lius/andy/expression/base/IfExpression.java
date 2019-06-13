package xyz.lius.andy.expression.base;

import xyz.lius.andy.expression.*;
import xyz.lius.andy.expression.ast.BracketExpression;
import xyz.lius.andy.expression.context.ExpressionContext;

import java.util.List;

public class IfExpression extends NativeExpression {
    @Override
    public Expression parameters(List<Expression> list) {
        return new IfExpression().list(list);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        Expression selectExpression;
        if (get(0).eval(context) == ExpressionType.TRUE) {
            selectExpression = get(1).getName().toString().equals("else") ? ((BracketExpression) get(1)).get(1) : get(1);
        } else {
            selectExpression = get(1).getName().toString().equals("else") ? ((BracketExpression) get(1)).get(2) : ExpressionType.NIL;
        }
        if (ExpressionUtils.isCurlyBracket(selectExpression)) {
            return selectExpression.eval(context).eval(new ExpressionContext());
        } else {
            return selectExpression.eval(context);
        }
    }
}
