package xyz.lbzh.andy.expression;

import xyz.lbzh.andy.util.Builder;

import java.util.List;

public class ContextBuilder implements Builder<Context<Name, Expression>> {
    private List<Expression> list;
    private Context<Name, Expression> parent;

    public ContextBuilder list(List<Expression> list) {
        this.list = list;
        return this;
    }

    public ContextBuilder parent(Context<Name, Expression> parent) {
        this.parent = parent;
        return this;
    }

    @Override
    public Context<Name, Expression> build() {
        Context<Name, Expression> context = new ExpressionContext(parent);
        this.list.stream().forEach(expression -> {
            expression.eval(context);
        });
        return context;
    }
}
