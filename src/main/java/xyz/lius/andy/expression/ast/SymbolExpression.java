package xyz.lius.andy.expression.ast;

import xyz.lius.andy.expression.Context;
import xyz.lius.andy.expression.Expression;
import xyz.lius.andy.expression.ExpressionUtils;
import xyz.lius.andy.expression.Name;

public class SymbolExpression extends TokenExpression {
    public SymbolExpression(String value) {
        this.value = value;
    }

    public SymbolExpression(String value, int lineNumber) {
        super(value, lineNumber);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        Expression rst = context.lookup(this);
        while (ExpressionUtils.isSymbol(rst)) {
            rst = context.lookup(rst.getName());
        }
        return rst;
    }
}
