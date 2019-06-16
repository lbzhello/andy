package xyz.lius.andy.expression.operator;

import xyz.lius.andy.core.OperatorSingleton;
import xyz.lius.andy.expression.*;
import xyz.lius.andy.expression.ast.ErrorExpression;
import xyz.lius.andy.util.AbstractContainer;

import java.math.BigDecimal;

@RoundBracketed
public class MultiplyExpression extends AbstractContainer implements Operator {
    public MultiplyExpression() {
        super(2);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        BigDecimal accu = BigDecimal.ONE;
        for (Expression expression : toArray()) {
            Expression factor = expression.eval(context);
            if (!(ExpressionUtils.isNumber(factor))) {
                return new ErrorExpression(expression, "Unsupport Operand Type!");
            }
            accu = accu.multiply(((BigDecimal) factor));
        }
        return ExpressionFactory.number(accu.doubleValue());
    }

    @Override
    public String toString() {
        return show(OperatorSingleton.MUL, super.toString());
    }
}
