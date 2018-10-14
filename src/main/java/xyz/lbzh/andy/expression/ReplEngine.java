package xyz.lbzh.andy.expression;

import xyz.lbzh.andy.core.Definition;

public class ReplEngine {
    private Context<Name, Expression> context = new ExpressionContext(Definition.getCoreContext());

    public Expression eval(Expression expression) {
        Expression rst;
        if (ExpressionUtils.isCurlyBracket(expression)) {
            Expression complex = expression.eval(context); //build and generate a runtime expression
            rst = complex.eval(new ExpressionContext(context));
        } else {
            rst = expression.eval(context);
        }

        if (ExpressionUtils.hasError(rst)) {
            System.err.println(rst);
        } else {
            System.out.println(rst);
        }
        return rst;
    }
}
