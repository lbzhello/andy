package xyz.lbzh.andy.expression.support;

import xyz.lbzh.andy.core.Definition;
import xyz.lbzh.andy.expression.Expression;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class BracketExpression implements Expression {
    private List<Expression> list = new LinkedList<>();

    public BracketExpression(Expression... expressions) {
        Collections.addAll(list, expressions);
    }

    public List<Expression> list() {
        return list;
    }

    public BracketExpression list(List<Expression> list) {
        this.list = list;
        return this;
    }

    public Expression first() {
        return this.list.size() >= 1 ? list.get(0) : Definition.NIL;
    }

    public Expression second() {
        return this.list.size() >= 2 ? list.get(1) : Definition.NIL;
    }

    public Expression third() {
        return this.list.size() >= 3 ? list.get(2) : Definition.NIL;
    }

    public List<Expression> tail() {
        return this.list.size() >= 2 ?this.list().subList(1, this.list().size()) : Collections.emptyList();
    }

    public BracketExpression add(Expression expression) {
        this.list.add(expression);
        return this;
    }

    public List<Expression> getParameters() {
        return list();
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        if (list() != null && list().size() > 0) {
            for (Expression expression : list()) {
                sb.append(expression + " ");
            }
            //remove the last space
            sb.replace(sb.length()-1, sb.length(), "");
        }
        return sb.toString();
    }
}
