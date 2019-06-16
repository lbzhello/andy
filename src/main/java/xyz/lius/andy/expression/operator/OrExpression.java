package xyz.lius.andy.expression.operator;

import xyz.lius.andy.core.Definition;
import xyz.lius.andy.core.OperatorSingleton;
import xyz.lius.andy.expression.*;

/**
 *  a || b
 */
public class OrExpression extends AbstractContainer implements Operator {

    public OrExpression() {
        super(2);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        Expression rst = get(0).eval(context);
        if (rst == Definition.NIL || rst == Definition.FALSE) {
            rst = get(1).eval(context);
        }
        return rst;
    }

    @Override
    public String toString() {
        return show(OperatorSingleton.OR, super.toString());
    }
}
