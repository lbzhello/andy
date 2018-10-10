package xyz.lbzh.andy.expression.function;

import xyz.lbzh.andy.expression.Context;
import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.ExpressionType;
import xyz.lbzh.andy.expression.Name;

import java.util.List;

public class AssignExpression extends NativeExpression {
    @Override
    public Expression build(List<Expression> list) {
        return new AssignExpression().list(list);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        context.bind(first().getName(), second().eval(context));
        return ExpressionType.NIL;
    }
}
