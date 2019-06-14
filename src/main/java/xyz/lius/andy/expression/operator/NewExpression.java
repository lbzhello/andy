package xyz.lius.andy.expression.operator;

import xyz.lius.andy.core.OperatorSingleton;
import xyz.lius.andy.expression.*;

/**
 * Create an javaObject from class file
 */
public class NewExpression extends AbstractContainer implements Operator {
    public NewExpression() {
        super(1);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        try {
            Class<?> c = Class.forName(get(0).toString());
            return ExpressionFactory.javaObject(c.getConstructor().newInstance());
        } catch (Exception e) {
            e.printStackTrace();
            return ExpressionFactory.error(get(0), e.getMessage());
        }
    }

    @Override
    public String toString() {
        return show(OperatorSingleton.NEW, super.toString());
    }
}
