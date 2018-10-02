package xyz.lbzh.andy.expression;


import java.io.Serializable;
import java.util.function.Consumer;

public interface Expression extends Nameable, Serializable {

    default Expression eval(Context<Name, Expression> context){
        return this;
    }

    default void expr(Consumer<Expression> action) {}

    default Expression shift() {
        return this;
    }

    default <T> T shift(Class<T> clazz) {
        return (T)shift();
    }

}
