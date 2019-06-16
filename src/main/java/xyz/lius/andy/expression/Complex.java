package xyz.lius.andy.expression;

/**
 * represent a loaded expression
 */
public interface Complex extends Expression {

    Context<Name, Expression> getContext();

    Expression[] getParameters();

    void setParameters(Expression[] parameters);

    Expression[] getCodes();

    void setCodes(Expression[] codes);

}
