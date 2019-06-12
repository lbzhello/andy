package xyz.lius.andy.expression.base;

import xyz.lius.andy.expression.Context;
import xyz.lius.andy.expression.Expression;
import xyz.lius.andy.expression.Name;

import java.util.List;

public class ReturnExpression extends NativeExpression {
    private Expression value;
    public ReturnExpression() {}
    public ReturnExpression(Expression value) {
        this.value = value;
    }

    @Override
    public Expression parameters(List<Expression> list) {
        return new ReturnExpression().list(list);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        return new ReturnExpression(first().eval(context));
    }

    public Expression getValue() {
        return value;
    }

    public void setValue(Expression value) {
        this.value = value;
    }
}
