package xyz.lius.andy.expression.base;

import xyz.lius.andy.expression.Expression;
import xyz.lius.andy.expression.ast.RoundBracketExpression;

import java.util.List;

public class NativeExpression extends RoundBracketExpression {
    public Expression parameters(List<Expression> list) {
        this.list(list);
        return this;
    }
}
