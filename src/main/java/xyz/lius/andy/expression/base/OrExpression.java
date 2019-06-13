package xyz.lius.andy.expression.base;

import xyz.lius.andy.expression.Context;
import xyz.lius.andy.expression.Expression;
import xyz.lius.andy.expression.ExpressionType;
import xyz.lius.andy.expression.Name;

import java.util.List;

/**
 *  a || b
 */
public class OrExpression extends NativeExpression {

    @Override
    public Expression parameters(List<Expression> list) {
        return new OrExpression().list(list);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        Expression rst = get(0).eval(context);
        if (rst == ExpressionType.NIL || rst == ExpressionType.FALSE) {
            rst = get(1).eval(context);
        }
        return rst;
    }
}
