package xyz.lbzh.andy.expression.support;

import xyz.lbzh.andy.expression.Context;
import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.Nameable;
import xyz.lbzh.andy.expression.RoundBracketed;

/**
 * (...)
 */
public class RoundBracketExpression extends BracketExpression implements Nameable {

    public RoundBracketExpression(Expression... expressions) {
        super(expressions);
    }

    public static RoundBracketExpression operator(Expression... expressions) {
        return new OperatorExpression(expressions);
    }

    @Override
    public String toString() {
        return "(" + super.toString() + ")";
    }

    @RoundBracketed
    private static class OperatorExpression extends RoundBracketExpression {
        public OperatorExpression(Expression... expressions) {
            super(expressions);
        }

    }

    private static class DefinitionExpression extends RoundBracketExpression {
        public DefinitionExpression(Expression name, CurlyBracketExpression curlyBracketExpression) {
            super(name, curlyBracketExpression);
        }

        @Override
        public Expression eval(Context<Expression, Object> context) {
            return null;
        }
    }

}
