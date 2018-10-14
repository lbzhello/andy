package xyz.lbzh.andy.expression.ast;

import xyz.lbzh.andy.expression.Context;
import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.ExpressionUtils;
import xyz.lbzh.andy.expression.Name;

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
