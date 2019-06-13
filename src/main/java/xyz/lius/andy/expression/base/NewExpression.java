package xyz.lius.andy.expression.base;

import xyz.lius.andy.expression.Context;
import xyz.lius.andy.expression.Expression;
import xyz.lius.andy.expression.ExpressionFactory;
import xyz.lius.andy.expression.Name;

import java.util.List;

/**
 * Create an javaObject from class file
 */
public class NewExpression extends NativeExpression {
    @Override
    public Expression parameters(List<Expression> list) {
        return new NewExpression().list(list);
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
}
