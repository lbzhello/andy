package xyz.lius.andy.expression.base;

import xyz.lius.andy.expression.*;
import xyz.lius.andy.expression.ast.ErrorExpression;

import java.math.BigDecimal;
import java.util.List;

@RoundBracketed
public class MinusExpression extends NativeExpression {

    @Override
    public Expression parameters(List<Expression> list) {
        return new MinusExpression().list(list);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        Expression leftExpression = first().eval(context);
        Expression rightExpression = second().eval(context);
        if (!(ExpressionUtils.isNumber(leftExpression))) {
            return new ErrorExpression(first(), "Unsupport Operand Type!");
        } else if (!(ExpressionUtils.isNumber(rightExpression))) {
            return new ErrorExpression(second(), "Unsupport Operand Type!");
        }

        BigDecimal leftValue = (BigDecimal) leftExpression;
        BigDecimal rightValue = (BigDecimal) rightExpression;


        return ExpressionFactory.number(leftValue.subtract(rightValue).doubleValue());
    }
}
