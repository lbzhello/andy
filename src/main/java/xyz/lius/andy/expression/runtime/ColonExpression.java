package xyz.lius.andy.expression.runtime;

import xyz.lius.andy.expression.*;
import xyz.lius.andy.expression.ast.BracketExpression;
import xyz.lius.andy.expression.ast.RoundBracketExpression;

import java.util.Collections;
import java.util.List;

/**
 * e.g. name: "some name"
 */
public class ColonExpression extends RoundBracketExpression {
    //e.g. (f a b) || f
    private Expression key;
    //e.g. "some text" || {...} || (...){...}
    private Expression value;

    public ColonExpression(Expression key, Expression value) {
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
