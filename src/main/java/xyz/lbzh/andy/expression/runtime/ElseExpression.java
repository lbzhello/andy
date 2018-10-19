package xyz.lbzh.andy.expression.runtime;

import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.ast.RoundBracketExpression;

public class ElseExpression extends RoundBracketExpression {
    public ElseExpression(Expression left, Expression right) {
        super(left, right);
    }
}
