package xyz.lius.andy.expression.ast;

import xyz.lius.andy.core.Definition;
import xyz.lius.andy.expression.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
