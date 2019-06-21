package xyz.lius.andy.expression.ast;

import xyz.lius.andy.expression.*;

public class ComplexExpression implements Complex {

    private Expression[] parameters;
    private Expression[] codes;

    private Context<Name, Expression> context;

    public ComplexExpression(Context<Name, Expression> context) {
        this.context = context;
    }

    //形参
    @Override
    public void setParameters(Expression[] parameters) {
        this.parameters = parameters;
        // param1 -> NameExpression.$0; param2 -> NameExpression.$1; ...
        for (int i = 0; i < this.parameters.length; i++) {
            context.bind(this.parameters[i].getName(), ExpressionFactory.symbol("$" + i));
        }
    }

    @Override
    public Expression[] getParameters() {
        return parameters;
    }

    @Override
    public Context<Name, Expression> getContext() {
        return context;
    }

    @Override
    public void setCodes(Expression[] codes) {
        this.codes = codes;
    }

    @Override
    public Expression[] getCodes() {
        return codes;
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        return this;
    }

}
