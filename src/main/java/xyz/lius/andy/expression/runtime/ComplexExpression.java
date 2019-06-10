package xyz.lius.andy.expression.runtime;

import xyz.lius.andy.expression.*;

import java.util.List;

@CurlyBracketed
public class ComplexExpression implements Expression {
    //represent expression it self
    private static final Name SELF = ExpressionFactory.symbol("self");

    private List<Expression> parameters;
    private List<Expression> codes;

    private Context<Name, Expression> context;

    public ComplexExpression(Context<Name, Expression> context) {
        this.context = context;
    }

    //形参
    public ComplexExpression parameters(List<Expression> parameters) {
        this.parameters = parameters;
        // param1 -> NameExpression.$0; param2 -> NameExpression.$1; ...
        for (int i = 0; i < this.parameters.size(); i++) {
            context.bind(this.parameters.get(i).getName(), ExpressionFactory.symbol("$" + i));
        }
        return this;
    }

    public Context<Name, Expression> getContext() {
        return context;
    }

    public ComplexExpression code(List<Expression> codes) {
        this.codes = codes;
        return this;
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        context.setParent(this.context);
        context.add(SELF, this);
        Expression rstValue = ExpressionType.NIL;
        for (Expression expression : this.codes) {
            rstValue = expression.eval(context);
            if (ExpressionUtils.isReturn(rstValue)) {
                return ExpressionUtils.asReturn(rstValue).getValue();
            }
            if (ExpressionUtils.hasError(rstValue)) {
                return rstValue.eval(context);
            }
        }
        return rstValue;
    }

}
