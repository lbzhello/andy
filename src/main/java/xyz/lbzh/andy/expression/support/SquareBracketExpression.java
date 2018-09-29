package xyz.lbzh.andy.expression.support;

import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.ExpressionFactory;

/**
 * [...]
 */
public class SquareBracketExpression extends BracketExpression {

    public SquareBracketExpression(Expression... expressions) {
        super(expressions);
    }

    /**
     * SquareBracketExpression => RoundBracketExpression
     * @return
     */
    @Override
    public Expression shift() {
        return ExpressionFactory.roundBracket().list(this.list());
    }

    @Override
    public String toString() {
        return "[" + super.toString() + "]";
    }
}
