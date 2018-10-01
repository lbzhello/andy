package xyz.lbzh.andy.expression.support;

import xyz.lbzh.andy.expression.*;

import java.util.Collections;
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

    public static RoundBracketExpression define(BracketExpression key, CurlyBracketExpression value) {
        return new DefineExpression(key, value);
    }

    public static RoundBracketExpression pair(Expression key, Expression value) {
        return new PairExpression(key, value);
    }

    @Override
    public List<Expression> getParameters() {
        return this.list().size() >= 2 ? this.list().subList(1, this.list().size()) : Collections.emptyList();
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
        private BracketExpression bracketExpression;
        private CurlyBracketExpression curlyBracketExpression;

        public DefineExpression(BracketExpression bracketExpression, CurlyBracketExpression curlyBracketExpression) {
            super(ExpressionType.DEFINE, bracketExpression, curlyBracketExpression);
            this.bracketExpression = bracketExpression;
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
            ComplexExpression complexExpression = this.curlyBracketExpression.eval(new ExpressionContext(context));
            complexExpression.parameters(bracketExpression.getParameters()).list(this.curlyBracketExpression.getEvalList());
            context.bind(bracketExpression.getName(), complexExpression);
            return complexExpression;
        }
    }

    /**
     * e.g. name: "some name"
     */
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
            Expression rstValue = ExpressionType.NIL;
            if (value instanceof CurlyBracketExpression) { //e.g. key(a, b, c): {...}
                rstValue = ExpressionFactory.define(key, (CurlyBracketExpression) value).eval(context);
            } else if (value instanceof DefineExpression) { //e.g. key: (a, b, c){...}
                value.eval(context);
            }

            context.bind(key.getName(), rstValue);
            return null;
        }
    }

}
