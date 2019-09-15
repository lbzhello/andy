package xyz.lius.andy.expression.operator;

import xyz.lius.andy.core.Definition;
import xyz.lius.andy.core.OperatorSingleton;
import xyz.lius.andy.expression.*;
import xyz.lius.andy.expression.ast.NumberExpression;
import xyz.lius.andy.util.AbstractContainer;

public class GeExpression extends AbstractContainer implements Operator {

    public GeExpression() {
        super(2);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        Expression left = get(0).eval(context);
        Expression right = get(1).eval(context);
        if (TypeCheck.isNumber(left) && TypeCheck.isNumber(right)) {
            if (((NumberExpression) left).doubleValue() >= ((NumberExpression) right).doubleValue()) {
                return Definition.TRUE;
            } else {
                return Definition.FALSE;
            }
        } else { //compare as string
            int flag = left.toString().compareTo(right.toString());
            if (flag >= 0) {
                return Definition.TRUE;
            } else {
                return Definition.FALSE;
            }
        }
    }

    @Override
    public String toString() {
        return show(OperatorSingleton.GE, super.toString());
    }
}
