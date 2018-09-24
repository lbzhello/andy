package xyz.lbzh.andy.expression.support;

import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.annotation.RoundBracketed;

import java.util.List;

/**
 * (...)
 */
public class RoundBracketExpression extends BracketExpression {

    public RoundBracketExpression(Expression... expressions) {
        super(expressions);
    }

    public BracketExpression sexpress() {
        return new RoundBracketExpression().list(list());
    }

    @Override
    public String toString() {
        return "(" + super.toString() + ")";
    }

    public static RoundBracketExpression operator(Expression... expressions) {
        return new OperatorExpression(expressions);
    }

    @RoundBracketed
    private static class OperatorExpression extends RoundBracketExpression {
        public OperatorExpression(Expression... expressions) {
            super(expressions);
        }

    }

}