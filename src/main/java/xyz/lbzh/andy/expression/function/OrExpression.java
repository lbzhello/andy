package xyz.lbzh.andy.expression.function;

import xyz.lbzh.andy.expression.Context;
import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.ExpressionType;
import xyz.lbzh.andy.expression.Name;
import xyz.lbzh.andy.expression.support.RoundBracketExpression;

import java.util.List;

/**
 *  a || b
 */
public class OrExpression extends NativeExpression {

    @Override
    public Expression build(List<Expression> list) {
        return new OrExpression().list(list);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        Expression rst;
        if ((rst = first().eval(context)) == ExpressionType.NIL) {
            rst = second().eval(context);
        }
        return rst;
    }
}
