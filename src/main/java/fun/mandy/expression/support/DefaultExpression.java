package fun.mandy.expression.support;

import fun.mandy.expression.Expression;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DefaultExpression implements Expression {
    public List<Expression> list;

    public DefaultExpression() {
        list = new LinkedList<>();
    }

    public DefaultExpression(Expression... expressions) {
        list = new LinkedList<>();
        Collections.addAll(list, expressions);
    }

    public List<Expression> list() {
        return list;
    }

    public DefaultExpression list(List<Expression> list) {
        this.list = list;
        return this;
    }
}
