package fun.mandy.expression.support;

import fun.mandy.expression.Context;
import fun.mandy.expression.Expression;
import fun.mandy.expression.Name;

import java.util.*;

public class ListExpression implements Expression {
    private List<Expression> list;

    public ListExpression(List<Expression> list){
        this.list = list;
    }

    public ListExpression(Expression... expressions) {
        list = new LinkedList<>();
        Collections.addAll(list, expressions);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        List<Expression> rst = new LinkedList<>();
        for (Expression expression : list) {
            rst.add(expression.eval(context));
        }
        return new ListExpression(rst);
    }

    public void addExpression(Expression expression) {
        this.list.add(expression);
    }

    /**
     * 转换为形参
     * e.g. (a,b,c) => ((a,$1),(b,$2),(c,$3))
     * @return
     */
    public Map<Name, Expression> toParameter() {
        Map<Name, Expression> map = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            map.put((Name)list.get(i), new SymbolExpression("$" + i));
        }
        return map;
    }

    /**
     * 转换为实参
     * e.g. (a,b,c) => (($1,a),($2,b),($3,c))
     * @return
     */
    public Map<Name, Expression> toArgument() {
        Map<Name, Expression> map = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            map.put(new SymbolExpression("$" + i),list.get(i));
        }
        return map;
    }

    @Override
    public String toString() {
        String listStr = list.toString();
        listStr = "(" + listStr.substring(1, listStr.length() - 1) + ")"; // '[...]' -> '(...)'
        return listStr;
    }

    public List<Expression> getList() {
        return list;
    }
}
