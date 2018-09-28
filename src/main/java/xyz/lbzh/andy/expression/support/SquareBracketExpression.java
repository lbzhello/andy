package xyz.lbzh.andy.expression.support;

import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.ExpressionBuilder;

/**
 * [...]
 */
public class SquareBracketExpression extends BracketExpression {

    public SquareBracketExpression(Expression... expressions) {
        super(expressions);
    }

    @Override
    public Expression shift() {
        return ExpressionBuilder.roundBracket().list(this.list());
    }

    @Override
    public String toString() {
        return "[" + super.toString() + "]";
    }
}
