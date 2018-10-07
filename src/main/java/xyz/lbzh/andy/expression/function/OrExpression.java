package xyz.lbzh.andy.expression.function;

import xyz.lbzh.andy.expression.Context;
import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.ExpressionType;
import xyz.lbzh.andy.expression.Name;
import xyz.lbzh.andy.expression.support.RoundBracketExpression;

/**
 *  a || b
 */
public class OrExpression extends RoundBracketExpression {
    private Expression left;
    private Expression right;

    public OrExpression(Expression left, Expression right) {
        super(ExpressionType.OR, left, right);
        this.left = left;
        this.right = right;
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        Expression rst = ExpressionType.NIL;
        if ((rst = left.eval(context)) == ExpressionType.NIL) {
            rst = right.eval(context);
        }
        return rst;
    }
}
