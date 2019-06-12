package xyz.lius.andy.expression;

import java.util.List;

public interface Complex extends Expression {

    Context<Name, Expression> getContext();

    List<Expression> getParameters();

    Complex setParameters(List<Expression> parameters);

    List<Expression> getCodes();

    Complex setCodes(List<Expression> codes);

}
