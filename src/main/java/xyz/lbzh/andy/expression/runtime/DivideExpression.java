package xyz.lbzh.andy.expression.runtime;

import xyz.lbzh.andy.expression.*;
import xyz.lbzh.andy.expression.ast.ErrorExpression;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@RoundBracketed
public class DivideExpression extends NativeExpression {

    @Override
    public Expression build(List<Expression> list) {
        return new DivideExpression().list(list);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        Expression leftExpression = first().eval(context);
        Expression rightExpression = second().eval(context);
        if (!(ExpressionUtils.isNumber(leftExpression))) {
            return ExpressionFactory.error(leftExpression, "Unsupport Operand Type!");
        } else if (!(ExpressionUtils.isNumber(rightExpression))) {
            return ExpressionFactory.error(rightExpression, "Unsupport Operand Type!");
        }

        BigDecimal leftValue = (BigDecimal) leftExpression;
        BigDecimal rightValue = (BigDecimal) rightExpression;


        return ExpressionFactory.number(leftValue.divide(rightValue, 2, RoundingMode.HALF_EVEN).doubleValue());
    }
}
