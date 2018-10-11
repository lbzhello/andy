package xyz.lbzh.andy.expression.function;

import xyz.lbzh.andy.expression.*;
import xyz.lbzh.andy.expression.support.CurlyBracketExpression;
import xyz.lbzh.andy.expression.support.RoundBracketExpression;

import java.util.List;

public class AssignExpression extends NativeExpression {
    @Override
    public Expression build(List<Expression> list) {
        return new AssignExpression().list(list);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        Name key = first().getName();
        Expression value;
        if (first() instanceof RoundBracketExpression) { //lambda
            RoundBracketExpression bracketExpression = (RoundBracketExpression) first();
            if (second() instanceof CurlyBracketExpression) { //e.g. f(x) = { x }
                CurlyBracketExpression curlyBracketExpression = (CurlyBracketExpression) second();
                value = curlyBracketExpression.eval(new ExpressionContext(context)).parameters(bracketExpression.getParameters());
            } else { //e.g. f(x) = x + 1
                value = ExpressionFactory.complex(new ExpressionContext(context)).parameters(bracketExpression.getParameters()).list(List.of(second()));
            }
        } else { //e.g. f = x + 1
            value = second().eval(context);
        }
        context.bind(key, value);
        return ExpressionType.NIL;
    }
}
