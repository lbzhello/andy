package xyz.lbzh.andy.expression.support;

import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.annotation.RoundBracketed;

@RoundBracketed
public class OperatorExpression extends RoundBracketExpression {
    public OperatorExpression(Expression... expressions) {
        super(expressions);
    }

}
