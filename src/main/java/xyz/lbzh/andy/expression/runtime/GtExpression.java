package xyz.lbzh.andy.expression.runtime;

import xyz.lbzh.andy.expression.*;
import xyz.lbzh.andy.expression.ast.NumberExpression;

import java.util.List;

/**
 * >
 */
public class GtExpression extends NativeExpression {
    @Override
    public Expression parameters(List<Expression> list) {
        return new GtExpression().list(list);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        Expression left = first().eval(context);
        Expression right = second().eval(context);
        if (ExpressionUtils.isNumber(left) && ExpressionUtils.isNumber(right)) {
            if (((NumberExpression) left).doubleValue() > ((NumberExpression) right).doubleValue()) {
                return ExpressionType.TRUE;
            } else {
                return ExpressionType.FALSE;
            }
        } else { //compare as string
            int flag = left.toString().compareTo(right.toString());
            if (flag > 0) {
                return ExpressionType.TRUE;
            } else {
                return ExpressionType.FALSE;
            }
        }
    }
}
