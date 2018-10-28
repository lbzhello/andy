package xyz.lbzh.andy.expression.ast;

import xyz.lbzh.andy.expression.Context;
import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.Name;

public class PairExpression implements Expression {
    private Expression key;
    private Expression value;

    public PairExpression(Expression key, Expression value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        Expression key = this.key.eval(context);
        Expression value = this.value.eval(context);
        return new PairExpression(key, value);
    }

}
