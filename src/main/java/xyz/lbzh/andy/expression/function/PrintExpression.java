package xyz.lbzh.andy.expression.function;

import xyz.lbzh.andy.expression.Context;
import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.ExpressionType;
import xyz.lbzh.andy.expression.Name;
import xyz.lbzh.andy.expression.support.RoundBracketExpression;
import xyz.lbzh.andy.expression.support.StringExpression;

import java.util.LinkedList;
import java.util.List;

public class PrintExpression extends NativeExpression {

    public PrintExpression() {}

    private PrintExpression(List<Expression> list) {
        list(list);
    }

    @Override
    public Expression build(List<Expression> list) {
        return new PrintExpression(list);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        List<Expression> rstList = new LinkedList<>();
        list().stream().forEach(element -> {
            rstList.add(element.eval(context));
        });
        System.out.println(rstList);
        return ExpressionType.NIL;
    }
}
