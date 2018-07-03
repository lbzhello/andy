package fun.mandy.expression.support;

import fun.mandy.core.Definition;
import fun.mandy.expression.Context;
import fun.mandy.expression.Expression;
import fun.mandy.expression.Name;

import java.util.List;

public class EvalExpression extends ListExpression {
    private Expression name;

    public EvalExpression(Expression name, List<Expression> list) {
        this.name = name;
        this.list = list;
    }


    public EvalExpression(Expression name, ListExpression args) {
        this.name = name;
        this.list = args.getList();
    }


    public EvalExpression(String name, Expression... list) {
        this(new SymbolExpression(name), new ListExpression(list));
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        if (name instanceof SymbolExpression && Definition.isOperator(name.toString())) { //原生函数

        } else {
//            Expression expression = context.lookup(name);
//            //如果是一个表达式名字,则循环查找到它所代表的值
//            while (expression instanceof SymbolExpression) {
//                expression = context.lookup(name);
//            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (Expression expression : this.getList()) {
            sb.append(" " + expression);
        }
        return "(" + this.name.toString() + sb.toString() + ")";
    }

    public Expression getName() {
        return name;
    }

    public void setName(Expression name) {
        this.name = name;
    }
}
