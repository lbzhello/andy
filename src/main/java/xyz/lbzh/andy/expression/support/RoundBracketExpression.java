package xyz.lbzh.andy.expression.support;

import xyz.lbzh.andy.expression.*;

/**
 * (...)
 */
public class RoundBracketExpression extends BracketExpression {

    public RoundBracketExpression(Expression... expressions) {
        super(expressions);
    }

    public static RoundBracketExpression operator(Expression... expressions) {
        return new OperatorExpression(expressions);
    }

    public static RoundBracketExpression define(Expression key, Expression value) {
        return new DefineExpression(key, value);
    }

    @Override
    public Name toName() {
        return this.first().toName();
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

    private static class DefineExpression extends RoundBracketExpression {
        private Expression key;
        private Expression value;
        public DefineExpression(Expression key, Expression value) {
            super(key, value);
            this.key = key;
            this.value = value;
        }

        @Override
        public Expression eval(Context<Name, Object> context) {
            context.bind(key.toName(), value);
            return this;
        }
    }

}
