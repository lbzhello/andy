package xyz.lius.andy.expression.runtime;

import xyz.lius.andy.expression.Context;
import xyz.lius.andy.expression.Expression;
import xyz.lius.andy.expression.Name;

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
