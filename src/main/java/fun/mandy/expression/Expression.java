package fun.mandy.expression;

import fun.mandy.expression.support.ValueExpression;

import java.io.Serializable;

public interface Expression extends Serializable {
    default Expression eval(Context<Name,Expression> context){
        return this;
    }

    default Expression eval(){
        return eval(null);
    }

    enum Type implements Expression {
        NIL, BEGIN
    }
}
