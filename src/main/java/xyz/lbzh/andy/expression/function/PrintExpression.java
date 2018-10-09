package xyz.lbzh.andy.expression.function;

import xyz.lbzh.andy.expression.Context;
import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.ExpressionType;
import xyz.lbzh.andy.expression.Name;
import xyz.lbzh.andy.expression.support.RoundBracketExpression;
import xyz.lbzh.andy.expression.support.StringExpression;

public class PrintExpression extends RoundBracketExpression {
    private Expression element;

    public PrintExpression(Expression element) {
        super(ExpressionType.PRINT, element);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        System.out.println(element);
        return ExpressionType.NIL;
    }
}
