package xyz.lius.andy.expression.operator;

import xyz.lius.andy.core.OperatorSingleton;
import xyz.lius.andy.expression.*;
import xyz.lius.andy.util.AbstractContainer;

public class PlusExpression extends AbstractContainer implements Operator {
    public PlusExpression() {
        super(2);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        Expression first = get(0).eval(context);
        Expression second = get(1).eval(context);
        if (TypeCheck.isSquareBracket(first) || TypeCheck.isSquareBracket(second)) { //e.g. [1 2 3] + 4
            return ExpressionUtils.link(first, second);
        } else if (TypeCheck.isString(first) || TypeCheck.isString(second)) { //e.g. "abc" + "xyz"
            return ExpressionFactory.string(first.toString() + second.toString());
        } else if (TypeCheck.isNumber(first) && TypeCheck.isNumber(second)){ //e.g. 3 + 2
            Double value = TypeCheck.asNumber(first).add(TypeCheck.asNumber(second)).doubleValue();
            return ExpressionFactory.number(value);
        } else {
            Expression error;
            if (!TypeCheck.isNumber(first)) {
                error = get(0);
            } else {
                error = get(1);
            }
            return ExpressionFactory.error(error,"operator(+): unsupport Operand Type!");
        }
    }

    @Override
    public String toString() {
        return show(OperatorSingleton.PLUS, super.toString());
    }
}
