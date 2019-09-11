package xyz.lius.andy.expression.adapter;

import xyz.lius.andy.expression.Expression;

/**
 * add 接口适配器
 */
public interface AddableExpressionAdapter extends Expression {
    void add(Expression expression);

    void add(Expression[] expressions);

    Expression getExpression();
}
