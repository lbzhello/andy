package xyz.lbzh.andy.expression.support;

import xyz.lbzh.andy.expression.*;
import xyz.lbzh.andy.expression.function.NativeExpression;

import java.util.Collections;
import java.util.List;

/**
 * (f x y)
 */
public class RoundBracketExpression extends BracketExpression {

    public RoundBracketExpression(Expression... expressions) {
        super(expressions);
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

    /**
     * e.g. (f x y)
     * @param context
     * @return
     */
    @Override
    public Expression eval(Context<Name, Expression> context) {
        if (list().size() == 0) return ExpressionType.NIL; //e.g. ()
        Expression name = first().eval(context);
        if (name == ExpressionType.NIL) return ExpressionType.NIL;
        if (name instanceof NativeExpression) {
            return ((NativeExpression) name).build(this.getParameters()).eval(context);
        }
        if (!(name instanceof ComplexExpression)) return ExpressionFactory.error("Expression must be ComplexExpression!");
        ComplexExpression complex = (ComplexExpression) name;
        Context<Name, Expression> childContext = new ExpressionContext(complex.getContext());
        //put args in context
        for (int i = 0; i < this.getParameters().size(); i++) {
            childContext.bind(ExpressionFactory.symbol("$" + i), this.tail().get(i).eval(context));
        }
        return complex.eval(childContext);
    }

    public static RoundBracketExpression operator(Expression... expressions) {
        return new OperatorExpression(expressions);
    }

    public static RoundBracketExpression lambda(BracketExpression bracket, CurlyBracketExpression curlyBracket) {
        return new LambdaExpression(bracket, curlyBracket);
    }

    public static RoundBracketExpression define(BracketExpression bracket, CurlyBracketExpression curlyBracket) {
        return new DefineExpression(bracket, curlyBracket);
    }

    public static RoundBracketExpression pair(Expression key, Expression value) {
        return new PairExpression(key, value);
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

    /**
     * e.g. (...){...}
     */
    @CurlyBracketed
    private static class LambdaExpression extends RoundBracketExpression {
        private BracketExpression bracket;
        private CurlyBracketExpression curlyBracket;

        public LambdaExpression(BracketExpression bracket, CurlyBracketExpression curlyBracket) {
            this.bracket = bracket;
            this.curlyBracket = curlyBracket;
        }

        @Override
        public Expression eval(Context<Name, Expression> context) {
            //every ComplexExpression has it's own context
            return this.curlyBracket.eval(new ExpressionContext(context)).parameters(bracket.getParameters());
        }
    }

    @CurlyBracketed
    private static class DefineExpression extends RoundBracketExpression {
        private BracketExpression bracket;
        private CurlyBracketExpression curlyBracket;

        public DefineExpression(BracketExpression bracket, CurlyBracketExpression curlyBracket) {
            super(ExpressionType.DEFINE, bracket, curlyBracket);
            this.bracket = bracket;
            this.curlyBracket = curlyBracket;
        }

        /**
         * Define will generate a ComplexExpression
         * @param context
         * @return
         */
        @Override
        public Expression eval(Context<Name, Expression> context) {
            //every ComplexExpression has it's own context
            ComplexExpression complexExpression = this.curlyBracket.eval(new ExpressionContext(context)).parameters(bracket.getParameters());
            context.bind(bracket.getName(), complexExpression);
            return complexExpression;
        }
    }

    /**
     * e.g. name: "some name"
     */
    private static class PairExpression extends RoundBracketExpression {
        //e.g. (f a b) || f
        private Expression key;
        //e.g. "some text" || {...} || (...){...}
        private Expression value;

        public PairExpression(Expression key, Expression value) {
            super(ExpressionType.PAIR, key, value);
            this.key = key;
            this.value = value;
        }

        @Override
        public Expression eval(Context<Name, Expression> context) {
            if (value instanceof CurlyBracketExpression || value instanceof LambdaExpression) {
                List<Expression> parameters = key instanceof BracketExpression ? ((BracketExpression) key).getParameters() : Collections.emptyList();
                ComplexExpression complexExpression = (ComplexExpression) value.eval(context);
                complexExpression.parameters(parameters);
                context.bind(key.getName(), complexExpression);
                return complexExpression;
            } else {
                context.bind(key.getName(), value);
                return value;
            }

        }
    }

}
