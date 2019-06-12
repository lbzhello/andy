package xyz.lius.andy.expression.base;

import xyz.lius.andy.core.Definition;
import xyz.lius.andy.expression.*;

import java.util.List;

@CurlyBracketed
public class ComplexExpression implements Complex {

    private List<Expression> parameters;
    private List<Expression> codes;

    private Context<Name, Expression> context;

    public ComplexExpression(Context<Name, Expression> context) {
        this.context = context;
    }

    //形参
    @Override
    public ComplexExpression setParameters(List<Expression> parameters) {
        this.parameters = parameters;
        // param1 -> NameExpression.$0; param2 -> NameExpression.$1; ...
        for (int i = 0; i < this.parameters.size(); i++) {
            context.bind(this.parameters.get(i).getName(), ExpressionFactory.symbol("$" + i));
        }
        return this;
    }

    @Override
    public List<Expression> getParameters() {
        return parameters;
    }

    @Override
    public Context<Name, Expression> getContext() {
        return context;
    }

    @Override
    public ComplexExpression setCodes(List<Expression> codes) {
        this.codes = codes;
        return this;
    }

    @Override
    public List<Expression> getCodes() {
        return codes;
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        context.setParent(this.context);
        context.add(Definition.SELF, this);
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
