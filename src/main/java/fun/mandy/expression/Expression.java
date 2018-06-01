package fun.mandy.expression;

import fun.mandy.context.Context;

public interface Expression {
    Expression eval(Context<Expression,Expression> context);
}
