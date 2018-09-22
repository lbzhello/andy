package xyz.lbzh.andy.expression.support;

import xyz.lbzh.andy.expression.Name;

public class SymbolExpression extends ValueExpression implements Name {
    public SymbolExpression(Object value) {
        this.value = value;
    }
}
