package fun.mandy.expression;

import fun.mandy.context.Context;

import java.io.Serializable;

public interface Expression extends Serializable {
    default Expression eval(Context<Expression,Expression> context){
        return this;
    };
}
