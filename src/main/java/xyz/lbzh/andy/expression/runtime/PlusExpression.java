package xyz.lbzh.andy.expression.runtime;

import xyz.lbzh.andy.expression.*;
import xyz.lbzh.andy.expression.ast.ErrorExpression;
import xyz.lbzh.andy.expression.ast.SquareBracketExpression;

import java.lang.annotation.ElementType;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

@RoundBracketed
public class PlusExpression extends NativeExpression {

    @Override
    public Expression parameters(List<Expression> list) {
        return new PlusExpression().list(list);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {

        Expression first = first().eval(context);
        Expression second = second().eval(context);
        if (ExpressionUtils.isSquareBracket(first) || ExpressionUtils.isSquareBracket(second)) { //e.g. [1 2 3] + 4
            return ExpressionUtils.pair(first, second);
        } else if (ExpressionUtils.isString(first) || ExpressionUtils.isString(second)) { //e.g. "abc" + "xyz"
            return ExpressionFactory.string(first.toString() + second.toString());
        } else if (ExpressionUtils.isNumber(first) && ExpressionUtils.isNumber(second)){ //e.g. 3 + 2
            Double value = ExpressionUtils.asNumber(first).add(ExpressionUtils.asNumber(second)).doubleValue();
            return ExpressionFactory.number(value);
        } else {
            return ExpressionFactory.error(ExpressionFactory.roundBracket(
                    ExpressionFactory.symbol("+"), first, second),"Unsupport Operand Type![+]");
        }
//        BigDecimal accu = BigDecimal.ZERO;
//        for (Expression expression : list()) {
//            Expression factor = expression.eval(context);
//            if (!(ExpressionUtils.isNumber(factor))) {
//                return new ErrorExpression(factor, "Unsupport Operand Type!");
//            }
//            accu = accu.add(((BigDecimal) factor));
//        }
//        return ExpressionFactory.number(accu.doubleValue());
    }

}
