package xyz.lius.andy.expression.runtime;

import xyz.lius.andy.expression.Expression;
import xyz.lius.andy.expression.ast.RoundBracketExpression;

public class ElseExpression extends RoundBracketExpression {
    public ElseExpression(Expression left, Expression right) {
        super(left, right);
    }
}
