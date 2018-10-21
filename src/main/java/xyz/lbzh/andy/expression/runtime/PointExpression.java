package xyz.lbzh.andy.expression.runtime;

import xyz.lbzh.andy.expression.*;
import xyz.lbzh.andy.expression.ast.RoundBracketExpression;
import xyz.lbzh.andy.expression.ast.SquareBracketExpression;

import java.util.List;

public class PointExpression extends NativeExpression {
    @Override
    public Expression parameters(List<Expression> list) {
        return new PointExpression().list(list);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        Expression left = first().eval(context);
        Expression right = second() instanceof RoundBracketExpression ? second().eval(context) : second();
        if (left instanceof ComplexExpression) { //e.g. left = { name:"liu" age:22 }  left.name
            return ((ComplexExpression) left).getContext().lookup(right.getName());
        } else if (ExpressionUtils.isSquareBracket(left)) { //e.g. left = [1 2 3 4]  left.map
            MethodExpression methodExpression = new MethodExpression(left);
            methodExpression.setMethodName(right.getName().toString());
            return methodExpression;
        }
        return ExpressionType.NIL;
    }
}
