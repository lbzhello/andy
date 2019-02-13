package xyz.lius.andy.expression.runtime;

import xyz.lius.andy.expression.*;
import xyz.lius.andy.expression.ast.BracketExpression;
import xyz.lius.andy.expression.ast.SquareBracketExpression;

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
        while (first().eval(context) == ExpressionType.TRUE) {
            squareBracketExpression.add(ExpressionUtils.isCurlyBracket(second()) ? second().eval(context).eval(new ExpressionContext())
                    : second().eval(context));
        }
        return squareBracketExpression;
    }
}
