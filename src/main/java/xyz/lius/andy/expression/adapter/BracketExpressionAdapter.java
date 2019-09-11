package xyz.lius.andy.expression.adapter;

import xyz.lius.andy.expression.Context;
import xyz.lius.andy.expression.Expression;
import xyz.lius.andy.expression.Name;
import xyz.lius.andy.expression.ast.BracketExpression;

public class BracketExpressionAdapter implements AddableExpressionAdapter {
    BracketExpression bracketExpression;

    public BracketExpressionAdapter(BracketExpression bracketExpression) {
        this.bracketExpression = bracketExpression;
    }

    @Override
    public void add(Expression expression) {
        bracketExpression.add(expression);
    }

    @Override
    public void add(Expression[] array) {
        bracketExpression.add(array);
    }

    @Override
    public Expression getExpression() {
        return bracketExpression;
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        return bracketExpression.eval(context);
    }
}
