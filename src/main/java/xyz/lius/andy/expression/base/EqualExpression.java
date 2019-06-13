package xyz.lius.andy.expression.base;

import xyz.lius.andy.expression.Context;
import xyz.lius.andy.expression.Expression;
import xyz.lius.andy.expression.ExpressionType;
import xyz.lius.andy.expression.Name;

import java.util.List;

public class EqualExpression extends NativeExpression {
    @Override
    public Expression parameters(List<Expression> list) {
        return new EqualExpression().list(list);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        if (get(0).eval(context).equals(get(1).eval(context))) {
            return ExpressionType.TRUE;
        } else {
            return ExpressionType.FALSE;
        }
    }
}
