package xyz.lbzh.andy.expression.function;

import xyz.lbzh.andy.expression.*;
import xyz.lbzh.andy.expression.internal.ErrorExpression;
import xyz.lbzh.andy.expression.support.NumberExpression;
import xyz.lbzh.andy.expression.support.RoundBracketExpression;

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
        if (!(ExpressionUtils.isNumber(leftExpression)) || !(ExpressionUtils.isNumber(rightExpression))) {
            return new ErrorExpression("Unsupport Operand Type!");
        }

        BigDecimal leftValue =  ((NumberExpression) leftExpression).getValue();
        BigDecimal rightValue = ((NumberExpression) rightExpression).getValue();


        return new NumberExpression(leftValue.divide(rightValue, 2, RoundingMode.HALF_EVEN));
    }
}
