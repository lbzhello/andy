package xyz.lbzh.andy.expression.runtime;

import xyz.lbzh.andy.expression.*;
import xyz.lbzh.andy.expression.ast.BracketExpression;
import xyz.lbzh.andy.expression.ast.RoundBracketExpression;

import java.util.Collections;
import java.util.List;

/**
 * e.g. name: "some name"
 */
public class PairExpression extends RoundBracketExpression {
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
        if (ExpressionUtils.isCurlyBracket(value) || ExpressionUtils.isLambda(value)) {
            List<Expression> parameters = key instanceof BracketExpression ? ((BracketExpression) key).getParameters() : Collections.emptyList();
            ComplexExpression complexExpression = (ComplexExpression) value.eval(context);
            complexExpression.parameters(parameters);
            context.newbind(key.getName(), complexExpression);
            return complexExpression;
        } else {
            context.newbind(key.getName(), value);
            return value;
        }

    }
}
