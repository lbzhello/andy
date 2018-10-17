package xyz.lbzh.andy.expression.runtime;

import xyz.lbzh.andy.expression.*;
import xyz.lbzh.andy.expression.ast.BracketExpression;

import java.util.List;

public class PrintExpression extends NativeExpression {

    @Override
    public Expression parameters(List<Expression> list) {
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
