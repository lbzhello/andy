package xyz.lbzh.andy.expression.support;

import xyz.lbzh.andy.expression.*;

import java.util.List;

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

    /**
     * RoundBracketExpression => SquareBracketExpression
     * @return
     */
    @Override
    public Expression shift() {
        return ExpressionFactory.squareBracket().list(this.list());
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
            return ExpressionFactory.roundBracket().list(this.list());
        }
    }

    private static class DefineExpression extends RoundBracketExpression {
        private Expression expression;
        private CurlyBracketExpression curlyBracketExpression;

        public DefineExpression(Expression expression, CurlyBracketExpression curlyBracketExpression) {
            super(ExpressionType.DEFINE, expression, curlyBracketExpression);
            this.expression = expression;
            this.curlyBracketExpression = curlyBracketExpression;
        }

        /**
         * Define will generate a ComplexExpression
         * @param context
         * @return
         */
        @Override
        public Expression eval(Context<Name, Object> context) {
            //every ComplexExpression has it's own context
            Context<Name, Object> complexContext = this.curlyBracketExpression.build(new ExpressionContext(context));
            ComplexExpression complexExpression = new ComplexExpression(complexContext);
            complexExpression.build(expression, curlyBracketExpression);
            context.bind(expression.getName(), complexExpression);
            return this;
        }
    }

    private static class PairExpression extends RoundBracketExpression {
        private Expression key;
        private Expression value;

        public PairExpression(Expression key, Expression value) {
            super(ExpressionType.PAIR, key, value);
            this.key = key;
            this.value = value;
        }

        @Override
        public Expression eval(Context<Name, Object> context) {
            return null;
        }
    }

}
