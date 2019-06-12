package xyz.lius.andy.expression;


import java.io.Serializable;

@FunctionalInterface
public interface Expression extends Serializable, Cloneable {
    Expression eval(Context<Name, Expression> context);

    /**
     * transfer an javaObject to a name
     * @return
     */
    default Name getName() {
        return Name.NIL;
    }
}
