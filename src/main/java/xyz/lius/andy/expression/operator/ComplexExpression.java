package xyz.lius.andy.expression.operator;

import xyz.lius.andy.core.Definition;
import xyz.lius.andy.expression.*;

@CurlyBracketed
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
