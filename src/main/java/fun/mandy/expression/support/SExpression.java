package fun.mandy.expression.support;

import fun.mandy.expression.Expression;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class SExpression implements Expression {
    protected List<Expression> list;

    public SExpression() {
        list = new LinkedList<>();
    }

    public SExpression(List<Expression> list) {
        this.list = list;
    }

    public SExpression(Expression... expressions) {
        list = new LinkedList<>();
        Collections.addAll(list, expressions);
    }

    public void add(Expression expression) {
        list.add(expression);
    }

    public Expression first() {
        return list.get(0);
    }


    public List<Expression> getList() {
        return this.list;
    }

    public SExpression toSExpression() {
        return new SExpression(list);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("(");
        if (list != null && list.size() > 0) {
            for (Expression expression : list) {
                sb.append(expression + " ");
            }
            //remove the last space
            sb.replace(sb.length()-1, sb.length(), "");
        }
        sb.append(")");
        return sb.toString();
    }
}
