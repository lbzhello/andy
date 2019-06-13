package xyz.lius.andy.expression.base;

import xyz.lius.andy.expression.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@RoundBracketed
public class DivideExpression extends NativeExpression {

    @Override
    public Expression parameters(List<Expression> list) {
        return new DivideExpression().list(list);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        Expression leftExpression = get(0).eval(context);
        Expression rightExpression = get(1).eval(context);
        if (!(ExpressionUtils.isNumber(leftExpression))) {
            return ExpressionFactory.error(get(0), "Unsupport Operand Type!");
        } else if (!(ExpressionUtils.isNumber(rightExpression))) {
            return ExpressionFactory.error(get(1), "Unsupport Operand Type!");
        }

        BigDecimal leftValue = (BigDecimal) leftExpression;
        BigDecimal rightValue = (BigDecimal) rightExpression;


        return ExpressionFactory.number(leftValue.divide(rightValue, 2, RoundingMode.HALF_EVEN).toString());
    }
}
