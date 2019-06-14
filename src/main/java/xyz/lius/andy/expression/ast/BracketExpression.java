package xyz.lius.andy.expression.ast;

import xyz.lius.andy.core.Definition;
import xyz.lius.andy.expression.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BracketExpression implements Expression {
    private List<Expression> list = new ArrayList<>();

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

    public Expression get(int i) {
        return i < this.list.size() ? list.get(i) : Definition.NIL;
    }

    public void add(Expression expression) {
        this.list.add(expression);
    }

    public boolean isEmpty() {
        return this.list.isEmpty();
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

    @Override
    public Expression eval(Context<Name, Expression> context) {
        return this;
    }
}
