package xyz.lbzh.andy.expression;

import xyz.lbzh.andy.core.Definition;
import xyz.lbzh.andy.expression.runtime.NativeExpression;

public class ReplEngine {
    private Context<Name, Expression> context = new ExpressionContext(Definition.getCoreContext());

    public Expression eval(Expression expression) {
        if (ExpressionUtils.isCurlyBracket(expression)) {
            Expression complex = expression.eval(context); //build and generate a runtime expression
            Expression rst = complex.eval(new ExpressionContext(context));
            if (ExpressionUtils.hasError(rst)) {
                System.err.println(rst);
            } else {
                System.out.println(rst);
            }
            return rst;
        } else {
            Expression rst = expression.eval(context);
            return rst;
        }
    }
}
