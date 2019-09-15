package xyz.lius.andy.expression.ast;

import xyz.lius.andy.expression.Context;
import xyz.lius.andy.expression.Expression;
import xyz.lius.andy.expression.TypeCheck;
import xyz.lius.andy.expression.Name;

public class IdentifierExpression extends ConstantExpression {
    public IdentifierExpression(String value) {
        this.value = value;
    }

    public IdentifierExpression(String value, int lineNumber) {
        super(value, lineNumber);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        Expression rst = context.lookup(this);
        while (TypeCheck.isSymbol(rst)) {
            rst = context.lookup(rst.getName());
        }
        return rst;
    }
}
