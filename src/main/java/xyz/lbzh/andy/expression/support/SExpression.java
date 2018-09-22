package xyz.lbzh.andy.expression.support;

import xyz.lbzh.andy.expression.Expression;

import java.util.List;

public class SExpression extends DefaultExpression {

    public SExpression(Expression... expressions) {
        super(expressions);
    }

    public SExpression(List<Expression> list) {
        list(list);
    }

    public SExpression sexpress() {
        return new SExpression(list());
    }

    @Override
    public String toString() {
        return "(" + super.toString() + ")";
    }
}
