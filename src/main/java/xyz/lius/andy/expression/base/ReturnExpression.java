package xyz.lius.andy.expression.base;

import xyz.lius.andy.expression.*;

public class ReturnExpression extends AbstractContainer implements Operator {
    private Expression value;
    public ReturnExpression() {}
    public ReturnExpression(Expression value) {
        super(1);
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
