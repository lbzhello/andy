package xyz.lbzh.andy.expression.function;

import xyz.lbzh.andy.expression.*;
import xyz.lbzh.andy.expression.internal.ErrorExpression;
import xyz.lbzh.andy.expression.support.NumberExpression;
import xyz.lbzh.andy.expression.support.RoundBracketExpression;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RoundBracketed
public class DivideExpression extends RoundBracketExpression {
    Expression left;
    Expression right;

    public DivideExpression(Expression left, Expression right) {
        super(ExpressionType.PLUS, left, right);
        this.left = left;
        this.right = right;
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        Expression leftExpression = left.eval(context);
        Expression rightExpression = right.eval(context);
        if (!(leftExpression instanceof NumberExpression) || !(rightExpression instanceof NumberExpression)) {
            return new ErrorExpression("Unsupport Operand Type!");
        }

        BigDecimal leftValue =  ((NumberExpression) leftExpression).getValue();
        BigDecimal rightValue = ((NumberExpression) rightExpression).getValue();


        return new NumberExpression(leftValue.divide(rightValue, 2, RoundingMode.HALF_EVEN));
    }
}
