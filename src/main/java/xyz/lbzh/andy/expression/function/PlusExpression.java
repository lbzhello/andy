package xyz.lbzh.andy.expression.function;

import xyz.lbzh.andy.expression.Context;
import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.ExpressionType;
import xyz.lbzh.andy.expression.Name;
import xyz.lbzh.andy.expression.support.RoundBracketExpression;

public class PlusExpression extends RoundBracketExpression {
    Expression left;
    Expression right;

    public PlusExpression(Expression left, Expression right) {
        super(ExpressionType.PLUS, left, right);
        this.left = left;
        this.right = right;
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        return null;
    }
}
