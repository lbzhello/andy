package xyz.lbzh.andy.expression;


import java.io.Serializable;

public interface Expression extends Nameable, Serializable {

    default Expression eval(Context<Name, Object> context){
        return this;
    }

}
