package xyz.lbzh.andy.expression.runtime;

import xyz.lbzh.andy.expression.*;

import java.util.List;

@CurlyBracketed
public class ComplexExpression implements Expression {
    private List<Expression> parameters;
    private List<Expression> list;

    private Context<Name, Expression> context;

    public ComplexExpression(Context<Name, Expression> context) {
        this.context = context;
    }

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

    public ComplexExpression list(List<Expression> list) {
        this.list = list;
        return this;
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        Expression rstValue = ExpressionType.NIL;
        for (Expression expression : this.list) {
            rstValue = expression.eval(context);
            if (ExpressionUtils.isReturn(expression) || ExpressionUtils.hasError(expression)) {
                return rstValue;
            }
        }
        return rstValue;
    }

}
