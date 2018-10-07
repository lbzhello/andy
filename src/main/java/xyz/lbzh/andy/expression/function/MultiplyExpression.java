package xyz.lbzh.andy.expression.function;

import xyz.lbzh.andy.expression.*;
import xyz.lbzh.andy.expression.internal.ErrorExpression;
import xyz.lbzh.andy.expression.support.NumberExpression;
import xyz.lbzh.andy.expression.support.RoundBracketExpression;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@RoundBracketed
public class MultiplyExpression extends RoundBracketExpression {
    private List<Expression> parameters;

    //e.g. (* 2 5)
    public MultiplyExpression(Expression... expressions) {
        super(expressions);
        parameters = Arrays.asList(expressions).subList(1, expressions.length);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        BigDecimal accu = BigDecimal.ONE;
        for (Expression expression : parameters) {
            Expression factor = expression.eval(context);
            if (!(factor instanceof NumberExpression)) return new ErrorExpression("Unsupport Operand Type!");
            accu = accu.multiply(((NumberExpression) factor).getValue());
        }
        return new NumberExpression(accu);
    }
}
