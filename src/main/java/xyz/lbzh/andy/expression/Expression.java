package xyz.lbzh.andy.expression;


import xyz.lbzh.andy.expression.support.RoundBracketExpression;

import java.io.Serializable;

public interface Expression extends Nameable, Serializable {

    default Expression eval(Context<Name, Object> context){
        return this;
    }

    default Expression shift() {
        return this;
    }


    default <T> T as(Class<T> clazz) {
        return (T)this;
    }

}
