package fun.mandy.expression.support;

import fun.mandy.context.Context;
import fun.mandy.expression.Expression;

public class SymbolExpression implements Expression {
    private String value;
    public SymbolExpression(String value){
        this.value = value;
    };
}
