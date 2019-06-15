package xyz.lius.andy.expression.operator;

import xyz.lius.andy.expression.*;
import xyz.lius.andy.expression.ast.BracketExpression;
import xyz.lius.andy.expression.ast.RoundBracketExpression;

import java.util.Collections;
import java.util.List;

/**
 * e.g. name: "some name"
 */
public class ColonExpression extends AbstractContainer implements Operator {

    public ColonExpression(Expression key, Expression value) {
        super(2);
        add(key);
        add(value);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        Expression key = get(0); //e.g. (f a b) || f
        Expression value = get(1); //e.g. "some text" || {...} || (...){...}
        if (ExpressionUtils.isCurlyBracket(value) || ExpressionUtils.isLambda(value)) {
            List<Expression> parameters = key instanceof BracketExpression ? ((BracketExpression) key).getParameters() : Collections.emptyList();
            ComplexExpression complexExpression = (ComplexExpression) value.eval(context);
            complexExpression.setParameters(parameters);
            context.add(key.getName(), complexExpression);
            return complexExpression;
        } else {
            context.add(key.getName(), value);
            return value;
        }

    }

    @Override
    public String toString() {
        return show(ExpressionType.DEFINE.toString(), super.toString());
    }
}