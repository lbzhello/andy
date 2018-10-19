package xyz.lbzh.andy.expression.runtime;

import xyz.lbzh.andy.expression.Context;
import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.Name;

import java.util.List;

/**
 * use to eval and create a ReturnExpression
 */
public class ReturnBuilderExpression extends NativeExpression {
    @Override
    public Expression parameters(List<Expression> list) {
        return new ReturnBuilderExpression().list(list);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        return new ReturnExpression(first().eval(context));
    }
}
