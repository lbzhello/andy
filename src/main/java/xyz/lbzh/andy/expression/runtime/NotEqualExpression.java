package xyz.lbzh.andy.expression.runtime;

import xyz.lbzh.andy.expression.Context;
import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.ExpressionType;
import xyz.lbzh.andy.expression.Name;

import java.util.List;

public class NotEqualExpression extends NativeExpression {
    @Override
    public Expression parameters(List<Expression> list) {
        return new NotEqualExpression().list(list);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        if (first().eval(context).equals(second().eval(context))) {
            return ExpressionType.FALSE;
        } else {
            return ExpressionType.TRUE;
        }
    }
}
