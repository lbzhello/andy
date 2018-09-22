package xyz.lbzh.andy.expression.support;

import xyz.lbzh.andy.expression.Expression;

public class ListExpression extends DefaultExpression {

    public ListExpression(Expression... expressions) {
        super(expressions);
    }

    @Override
    public String toString() {
        return "[" + super.toString() + "]";
    }
}
