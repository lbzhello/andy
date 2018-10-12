package xyz.lbzh.andy.expression.function;

import xyz.lbzh.andy.expression.Context;
import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.ExpressionType;
import xyz.lbzh.andy.expression.Name;
import xyz.lbzh.andy.expression.support.ComplexExpression;
import xyz.lbzh.andy.expression.support.RoundBracketExpression;

import java.util.List;

public class PointExpression extends NativeExpression {
    @Override
    public Expression build(List<Expression> list) {
        return new PointExpression().list(list);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        Expression left = first().eval(context);
        Expression right = second() instanceof RoundBracketExpression ? second().eval(context) : second();
        if (left instanceof ComplexExpression) { //e.g. left = { name:"liu" age:22 }  left.name
            return ((ComplexExpression) left).getContext().lookup(right.getName());
        }
        return ExpressionType.NIL;
    }
}
