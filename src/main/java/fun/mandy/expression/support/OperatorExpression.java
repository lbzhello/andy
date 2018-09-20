package fun.mandy.expression.support;

import fun.mandy.expression.Expression;
import fun.mandy.expression.annotation.SExpressed;

@SExpressed
public class OperatorExpression extends SExpression{
    public OperatorExpression(Expression... expressions) {
        super(expressions);
    }

}
