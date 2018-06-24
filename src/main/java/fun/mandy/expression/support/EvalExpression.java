package fun.mandy.expression.support;

import fun.mandy.boot.Application;
import fun.mandy.constant.Constants;
import fun.mandy.expression.Context;
import fun.mandy.expression.Expression;
import fun.mandy.expression.Name;
import sun.awt.Symbol;

import java.util.List;

public class EvalExpression implements Expression {
    private Expression name;
    private ListExpression args;

    public EvalExpression(Expression name, ListExpression args) {
        this.name = name;
        this.args = args;
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        if (name instanceof SymbolExpression && Application.isOperator(name.toString())) { //原生函数

        } else {
//            Expression expression = context.lookup(name);
//            //如果是一个表达式名字,则循环查找到它所代表的值
//            while (expression instanceof SymbolExpression) {
//                expression = context.lookup(name);
//            }
        }
        return null;
    }

}
