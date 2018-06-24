package fun.mandy.expression.support;

import fun.mandy.expression.Context;
import fun.mandy.expression.Expression;
import fun.mandy.expression.Name;

public class SymbolExpression extends ObjectExpression implements Name {
    public SymbolExpression(){}

    public SymbolExpression(Object value) {
        this.value = value;
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        return new SymbolExpression(this.value);
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}
