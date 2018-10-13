package xyz.lbzh.andy.expression.runtime;

import xyz.lbzh.andy.expression.*;
import xyz.lbzh.andy.expression.support.BracketExpression;

import java.util.List;

public class PrintExpression extends NativeExpression {

    @Override
    public Expression build(List<Expression> list) {
        return new PrintExpression().list(list);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        BracketExpression rst = ExpressionFactory.bracket();
        list().stream().forEach(element -> {
            rst.add(element.eval(context));
        });
        System.out.println(rst);
        return ExpressionType.NIL;
    }
}
