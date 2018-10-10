package xyz.lbzh.andy.expression;

import java.util.HashMap;
import java.util.Map;

public class ExpressionContext implements Expression, Context<Name, Expression> {
    private Map<Name, Expression> container = new HashMap<>();
    private Context<Name, Expression> parent = null;

    public ExpressionContext(Context<Name, Expression> parent) {
        this.parent = parent;
    }

    @Override
    public Expression lookup(Name key) {
        Expression o = container.getOrDefault(key, ExpressionType.NIL);
        if (o == ExpressionType.NIL && this.parent != null) {
            o = parent.lookup(key);
        }
        return o;
    }

    @Override
    public ExpressionContext bind(Name key, Expression value) {
        this.container.put(key, value);
        return this;
    }

}
