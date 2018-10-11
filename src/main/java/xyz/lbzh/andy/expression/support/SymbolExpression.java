package xyz.lbzh.andy.expression.support;

import xyz.lbzh.andy.expression.Context;
import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.Name;

public class SymbolExpression extends ValueExpression {
    public SymbolExpression(Object value) {
        this.value = value;
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        Expression rst = context.lookup(this);
        while (rst instanceof SymbolExpression) {
            rst = context.lookup(rst.getName());
        }
        return rst;
    }
}
