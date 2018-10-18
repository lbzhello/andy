package xyz.lbzh.andy.expression.runtime;

import xyz.lbzh.andy.expression.*;

import java.util.List;

public class IfExpression extends NativeExpression {
    @Override
    public Expression parameters(List<Expression> list) {
        return new IfExpression().list(list);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        if (first().eval(context) == ExpressionType.TRUE) {
            if (ExpressionUtils.isCurlyBracket(second())) {

            }
            return second().eval(context);
        }
        return ExpressionType.NIL;
    }
}
