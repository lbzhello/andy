package xyz.lius.andy.expression.runtime;

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
        Expression rst = first().eval(context);
        if (rst == ExpressionType.NIL || rst == ExpressionType.FALSE) {
            rst = second().eval(context);
        }
        return rst;
    }
}
