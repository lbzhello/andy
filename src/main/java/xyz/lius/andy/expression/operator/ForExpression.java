package xyz.lius.andy.expression.operator;

import xyz.lius.andy.core.OperatorSingleton;
import xyz.lius.andy.expression.*;
import xyz.lius.andy.expression.ast.BracketExpression;
import xyz.lius.andy.expression.context.ExpressionContext;

/**
 * e.g. for(first, second)
 */
public class ForExpression extends AbstractContainer implements Operator {
    public ForExpression() {
        super(2);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        BracketExpression squareBracketExpression = ExpressionFactory.squareBracket();
        while (get(0).eval(context) == ExpressionType.TRUE) {
            squareBracketExpression.add(ExpressionUtils.isCurlyBracket(get(1)) ? get(1).eval(context).eval(new ExpressionContext())
                    : get(1).eval(context));
        }
        return squareBracketExpression;
    }

    @Override
    public String toString() {
        return show(OperatorSingleton.FOR, super.toString());
    }
}
