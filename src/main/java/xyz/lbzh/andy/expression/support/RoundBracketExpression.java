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

    public static RoundBracketExpression define(Expression key, CurlyBracketExpression value) {
        return new DefineExpression(key, value);
    }

    @Override
    public Expression shift() {
        return ExpressionBuilder.squareBracket().list(this.list());
    }

    @Override
    public Name getName() {
        return this.first().getName();
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

        @Override
        public Expression shift() {
            return ExpressionBuilder.roundBracket().list(this.list());
        }
    }

    private static class DefineExpression extends RoundBracketExpression {
        private Expression key;
        private CurlyBracketExpression value;
        public DefineExpression(Expression key, CurlyBracketExpression value) {
            super(key, value);
            this.key = key;
            this.value = value;
        }

        @Override
        public Expression eval(Context<Name, Object> context) {
            context.bind(key.getName(), value);
            return this;
        }
    }

    private static class PairExpression extends RoundBracketExpression {
        private Expression key;
        private Expression value;

        public PairExpression(Expression key, Expression value) {
            super(key, value);
            this.key = key;
            this.value = value;
        }

        @Override
        public Expression eval(Context<Name, Object> context) {
            return null;
        }
    }

}
