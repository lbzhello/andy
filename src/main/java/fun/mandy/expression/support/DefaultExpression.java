package fun.mandy.expression.support;

import fun.mandy.core.Definition;
import fun.mandy.expression.Expression;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DefaultExpression implements Expression {
    private List<Expression> list;

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

    public Expression first() {
        return this.list.size() == 0 ? Definition.NIL : list.get(0);
    }

    public DefaultExpression add(Expression expression) {
        this.list.add(expression);
        return this;
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
