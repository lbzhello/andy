package xyz.lius.andy.expression.base;

import xyz.lius.andy.expression.*;
import xyz.lius.andy.expression.ast.ErrorExpression;

import java.math.BigDecimal;
import java.util.List;

@RoundBracketed
public class MultiplyExpression extends NativeExpression {

    @Override
    public Expression parameters(List<Expression> list) {
        return new MultiplyExpression().list(list);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        BigDecimal accu = BigDecimal.ONE;
        for (Expression expression : list()) {
            Expression factor = expression.eval(context);
            if (!(ExpressionUtils.isNumber(factor))) {
                return new ErrorExpression(expression, "Unsupport Operand Type!");
            }
            accu = accu.multiply(((BigDecimal) factor));
        }
        return ExpressionFactory.number(accu.doubleValue());
    }
}