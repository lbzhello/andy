package xyz.lius.andy.expression.operator;

import xyz.lius.andy.core.OperatorSingleton;
import xyz.lius.andy.expression.*;
import xyz.lius.andy.expression.ast.BracketExpression;
import xyz.lius.andy.util.AbstractContainer;

/**
 * e.g. name: "some name"
 */
public class ColonExpression extends AbstractContainer implements Operator {

    public ColonExpression() {
        super(2);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        Expression key = get(0); //e.g. (f a b) || f
        Expression value = get(1); //e.g. "some text" || {...} || (...){...}
        if (ExpressionUtils.isCurlyBracket(value) || ExpressionUtils.isLambda(value)) {
            Expression[] parameters = key instanceof BracketExpression ? ((BracketExpression) key).getParameters() : EMPTY_ELEMENT_DATA;
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
        return show(OperatorSingleton.COLON, super.toString());
    }
}
