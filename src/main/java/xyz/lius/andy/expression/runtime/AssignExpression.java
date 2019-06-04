package xyz.lius.andy.expression.runtime;

import xyz.lius.andy.expression.*;
import xyz.lius.andy.expression.ast.CurlyBracketExpression;
import xyz.lius.andy.expression.ast.RoundBracketExpression;

import java.util.List;
import java.util.Objects;

public class AssignExpression extends NativeExpression {
    @Override
    public Expression parameters(List<Expression> list) {
        return new AssignExpression().list(list);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        Context<Name, Expression> bindContext = context;
        Name name;
        Expression value;
        if (first() instanceof RoundBracketExpression) { //lambda (f x) = ...
            RoundBracketExpression left = (RoundBracketExpression) this.first();
            if (left instanceof PointExpression) { //e.g. (. a b) = ...
                Expression parent = left.second().eval(context);
                if (parent instanceof ComplexExpression) {
                    bindContext = ((ComplexExpression) parent).getContext();
                    name = left.third() instanceof RoundBracketExpression ? left.third().eval(context).getName() : left.third().getName();
                    value = this.second().eval(context);
                } else {
                    return ExpressionFactory.error(parent, "Left value should be ComplexExpression");
                }
            } else if (second() instanceof CurlyBracketExpression) { //define a function. e.g. f(x) = { x }
                CurlyBracketExpression right = (CurlyBracketExpression) second();
                name = left.getName();
                value = right.eval(context).parameters(left.getParameters());
            } else { //e.g. f(x) = x + 1
                name = left.getName();
                value = ExpressionFactory.complex(new ExpressionContext(context)).parameters(left.getParameters()).list(List.of(second()));
            }
        } else { //e.g. f = x + 1
            name = this.first().getName();
            value = this.second().eval(context);
        }
        bindContext.bind(name, value);
        return ExpressionType.NIL;
    }
}
