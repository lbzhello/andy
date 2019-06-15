package xyz.lius.andy.expression.ast;

import xyz.lius.andy.expression.AbstractContainer;
import xyz.lius.andy.expression.Context;
import xyz.lius.andy.expression.Expression;
import xyz.lius.andy.expression.Name;

public class BracketExpression extends AbstractContainer implements Expression {
    public BracketExpression(Expression... expressions) {
        super(expressions);
    }

    public Expression[] getParameters() {
        return toArray();
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        return this;
    }
}
