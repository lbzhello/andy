package xyz.lbzh.andy.expression.function;

import xyz.lbzh.andy.expression.*;
import xyz.lbzh.andy.expression.internal.ErrorExpression;
import xyz.lbzh.andy.expression.support.NumberExpression;
import xyz.lbzh.andy.expression.support.RoundBracketExpression;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@RoundBracketed
public class MultiplyExpression extends NativeExpression {

    @Override
    public Expression build(List<Expression> list) {
        return new MultiplyExpression().list(list);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        BigDecimal accu = BigDecimal.ONE;
        for (Expression expression : list()) {
            Expression factor = expression.eval(context);
            if (!(factor instanceof NumberExpression)) return new ErrorExpression("Unsupport Operand Type!");
            accu = accu.multiply(((NumberExpression) factor).getValue());
        }
        return new NumberExpression(accu);
    }
}
