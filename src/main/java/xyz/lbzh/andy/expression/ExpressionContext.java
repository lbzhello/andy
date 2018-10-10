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
    public Expression bind(Name key, Expression value) {
        if (!contains(key)) { //new key
            return this.container.put(key, value);
        }
        if (container.containsKey(key)) { //update key
            return container.put(key, value);
        } else if (parent != null) {
            return parent.bind(key, value);
        } else {
            return null;
        }
    }

    @Override
    public boolean contains(Name key) {
        return container.containsKey(key) || parent != null && parent.contains(key);
    }

}
