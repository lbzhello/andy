package xyz.lbzh.andy.expression.support;

import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.annotation.SExpressed;

@SExpressed
public class OperatorExpression extends SExpression{
    public OperatorExpression(Expression... expressions) {
        super(expressions);
    }

}
