package xyz.lius.andy.expression.base;

import xyz.lius.andy.expression.*;

import java.util.List;

@RoundBracketed
public class PlusExpression extends NativeExpression {

    @Override
    public Expression parameters(List<Expression> list) {
        return new PlusExpression().list(list);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {

        Expression first = get(0).eval(context);
        Expression second = get(1).eval(context);
        if (ExpressionUtils.isSquareBracket(first) || ExpressionUtils.isSquareBracket(second)) { //e.g. [1 2 3] + 4
            return ExpressionUtils.pair(first, second);
        } else if (ExpressionUtils.isString(first) || ExpressionUtils.isString(second)) { //e.g. "abc" + "xyz"
            return ExpressionFactory.string(first.toString() + second.toString());
        } else if (ExpressionUtils.isNumber(first) && ExpressionUtils.isNumber(second)){ //e.g. 3 + 2
            Double value = ExpressionUtils.asNumber(first).add(ExpressionUtils.asNumber(second)).doubleValue();
            return ExpressionFactory.number(value);
        } else {
            Expression error;
            if (!ExpressionUtils.isNumber(first)) {
                error = get(0);
            } else {
                error = get(1);
            }
            return ExpressionFactory.error(error,"operator(+): unsupport Operand Type!");
        }
//        BigDecimal accu = BigDecimal.ZERO;
//        for (Expression expression : setCodes()) {
//            Expression factor = expression.eval(context);
//            if (!(ExpressionUtils.isNumber(factor))) {
//                return new ErrorExpression(factor, "Unsupport Operand Type!");
//            }
//            accu = accu.add(((BigDecimal) factor));
//        }
//        return ExpressionFactory.number(accu.doubleValue());
    }

}
