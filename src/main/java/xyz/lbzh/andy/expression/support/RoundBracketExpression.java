package xyz.lbzh.andy.expression.support;

import xyz.lbzh.andy.expression.Expression;

import java.util.List;

/**
 * (...)
 */
public class RoundBracketExpression extends BracketExpression {

    public RoundBracketExpression(Expression... expressions) {
        super(expressions);
    }

    public RoundBracketExpression(List<Expression> list) {
        list(list);
    }

    public RoundBracketExpression sexpress() {
        return new RoundBracketExpression(list());
    }

    @Override
    public String toString() {
        return "(" + super.toString() + ")";
    }
}
