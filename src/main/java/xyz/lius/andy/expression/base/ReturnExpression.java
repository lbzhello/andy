package xyz.lius.andy.expression.base;

import xyz.lius.andy.expression.AbstructAddable;
import xyz.lius.andy.expression.Context;
import xyz.lius.andy.expression.Expression;
import xyz.lius.andy.expression.Name;
import xyz.lius.andy.expression.ast.BracketExpression;

import java.util.List;

public class ReturnExpression extends AbstructAddable {
    private Expression value;
    public ReturnExpression() {}
    public ReturnExpression(Expression value) {
        this.value = value;
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        return new ReturnExpression(get(0).eval(context));
    }

    public Expression getValue() {
        return value;
    }

    public void setValue(Expression value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "(return " + super.toString() + ")";
    }
}
