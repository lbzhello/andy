package xyz.lbzh.andy.expression.support;

import xyz.lbzh.andy.expression.Expression;

/**
 * [...]
 */
public class SquareBracketExpression extends BracketExpression {

    public SquareBracketExpression(Expression... expressions) {
        super(expressions);
    }

    @Override
    public String toString() {
        return "[" + super.toString() + "]";
    }
}
