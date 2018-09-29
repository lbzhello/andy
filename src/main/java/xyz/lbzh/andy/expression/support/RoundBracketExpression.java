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
            super(expression, curlyBracketExpression);
            this.expression = expression;
            this.curlyBracketExpression = curlyBracketExpression;
        }

        @Override
        public Expression eval(Context<Name, Object> context) {
            this.curlyBracketExpression.build(context);
            ComplexExpression complexExpression = new ComplexExpression(context);
            if (expression instanceof RoundBracketExpression) { //e.g. (a b c){...}
                RoundBracketExpression nameAndType = (RoundBracketExpression) expression;
                Expression name = nameAndType.first();
                List<Expression> params = nameAndType.tail();

                for (Expression param : params) {

                }
            }
            context.bind(expression.getName(), curlyBracketExpression);
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
