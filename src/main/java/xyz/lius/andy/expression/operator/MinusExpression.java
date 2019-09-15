package xyz.lius.andy.expression.operator;

import xyz.lius.andy.core.OperatorSingleton;
import xyz.lius.andy.expression.*;
import xyz.lius.andy.expression.ast.ErrorExpression;
import xyz.lius.andy.util.AbstractContainer;

import java.math.BigDecimal;

public class MinusExpression extends AbstractContainer implements Operator {
    public MinusExpression() {
        super(2);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        Expression leftExpression = get(0).eval(context);
        Expression rightExpression = get(1).eval(context);
        if (!(TypeCheck.isNumber(leftExpression))) {
            return new ErrorExpression(get(0), "Unsupport Operand Type!");
        } else if (!(TypeCheck.isNumber(rightExpression))) {
            return new ErrorExpression(get(1), "Unsupport Operand Type!");
        }

        BigDecimal leftValue = (BigDecimal) leftExpression;
        BigDecimal rightValue = (BigDecimal) rightExpression;


        return ExpressionFactory.number(leftValue.subtract(rightValue).doubleValue());
    }

    @Override
    public String toString() {
        return show(OperatorSingleton.MINUS, super.toString());
    }
}
