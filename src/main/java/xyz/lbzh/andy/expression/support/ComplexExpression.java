package xyz.lbzh.andy.expression.support;

import xyz.lbzh.andy.expression.Context;
import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.Name;

import java.util.List;

public class ComplexExpression implements Expression {
    private Name name;
    private List<Expression> parameters;

    private Context<Name, Object> context;

    public ComplexExpression(Context<Name, Object> context) {
        this.context = context;
    }



}
