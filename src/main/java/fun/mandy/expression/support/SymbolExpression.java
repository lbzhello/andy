package fun.mandy.expression.support;

import fun.mandy.expression.Name;

public class SymbolExpression extends ValueExpression implements Name {
    public SymbolExpression(Object value) {
        this.value = value;
    }
}
