package fun.mandy.expression.support;

import fun.mandy.expression.Expression;

public class ListExpression extends DefaultExpression {

    public ListExpression(Expression... expressions) {
        super(expressions);
    }

    @Override
    public String toString() {
        return "[" + super.toString() + "]";
    }
}
