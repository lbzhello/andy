package xyz.lbzh.andy.expression.function;

import xyz.lbzh.andy.expression.*;
import xyz.lbzh.andy.expression.internal.ErrorExpression;

import java.math.BigDecimal;
import java.util.List;

@RoundBracketed
public class MinusExpression extends NativeExpression {

    @Override
    public Expression build(List<Expression> list) {
        return new MinusExpression().list(list);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        Expression leftExpression = first().eval(context);
        Expression rightExpression = second().eval(context);
        if (!(ExpressionUtils.isNumber(leftExpression)) || !(ExpressionUtils.isNumber(rightExpression))) {
            return new ErrorExpression("Unsupport Operand Type!");
        }

        BigDecimal leftValue = (BigDecimal) leftExpression;
        BigDecimal rightValue = (BigDecimal) rightExpression;


        return ExpressionFactory.number(leftValue.subtract(rightValue).doubleValue());
    }
}
