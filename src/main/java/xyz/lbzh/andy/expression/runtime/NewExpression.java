package xyz.lbzh.andy.expression.runtime;

import xyz.lbzh.andy.expression.*;

/**
 * Create an object from class file
 */
public class NewExpression extends NativeExpression {
    @Override
    public Expression eval(Context<Name, Expression> context) {
        try {
            Class<?> c = Class.forName(first().toString());
            return ExpressionFactory.object(c.getConstructor().newInstance());
        } catch (Exception e) {
            e.printStackTrace();
            return ExpressionFactory.error(first(), e.getMessage());
        }
    }
}
