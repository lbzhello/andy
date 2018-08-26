package fun.mandy.expression;

import fun.mandy.expression.support.ValueExpression;

import java.io.Serializable;

public interface Expression extends Serializable {
    /**
     * End of file
     */
    ValueExpression EOF = new ValueExpression("-_^");

    /**
     * Head of file
     */
    ValueExpression HOF = new ValueExpression("^v^");

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
