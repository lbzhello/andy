package xyz.lius.andy.expression;

import xyz.lius.andy.core.Definition;
import xyz.lius.andy.expression.runtime.ComplexExpression;

import java.util.List;

public class StackFrame extends AbstractContext<Name, Expression> implements Expression {

    private List<Expression> codes;

    public StackFrame(ComplexExpression complex, Context<Name, Expression> argsContext, List<Expression> args) {
        super(complex.getContext());
        this.codes = complex.getCodes();
        List<Expression> params = complex.getParameters();
        if (!params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                bind(complex.getParameters().get(i).getName(), args.get(i).eval(argsContext));
            }
        }
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
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

    @Override
    public Expression lookup(Name key) {
        Expression o = super.lookup(key);
        return o == null ? ExpressionType.NIL : o;
    }

}
