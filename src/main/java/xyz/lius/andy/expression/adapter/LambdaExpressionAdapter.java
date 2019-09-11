package xyz.lius.andy.expression.adapter;

import xyz.lius.andy.expression.Context;
import xyz.lius.andy.expression.Expression;
import xyz.lius.andy.expression.Name;
import xyz.lius.andy.expression.operator.LambdaExpression;

/**
 * add 接口适配器
 */
public class LambdaExpressionAdapter implements AddableExpressionAdapter {
    LambdaExpression lambda;

    public LambdaExpressionAdapter(LambdaExpression lambda) {
        this.lambda = lambda;
    }

    @Override
    public void add(Expression expression) {
        lambda.add(expression);
    }

    @Override
    public void add(Expression[] expressions) {
        lambda.add(expressions);
    }

    @Override
    public Expression getExpression() {
        return lambda;
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        return lambda.eval(context);
    }
}
