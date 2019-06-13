package xyz.lius.andy.expression.base;

import xyz.lius.andy.expression.*;
import xyz.lius.andy.expression.ast.BracketExpression;
import xyz.lius.andy.expression.context.ExpressionContext;

import java.util.List;

/**
 * e.g. for(first, second)
 */
public class ForExpression extends NativeExpression {
    @Override
    public Expression parameters(List<Expression> list) {
        return new ForExpression().list(list);
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
}
