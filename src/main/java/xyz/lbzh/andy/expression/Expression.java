package xyz.lbzh.andy.expression;


import java.io.Serializable;

public interface Expression extends Serializable {

    default Expression eval(Context<Expression, Object> context){
        return this;
    }

    default Expression eval(){
        return eval(null);
    }

    enum Type implements Expression {
        NIL, BEGIN
    }
}
