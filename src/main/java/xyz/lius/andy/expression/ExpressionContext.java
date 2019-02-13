package xyz.lius.andy.expression;

import java.util.HashMap;
import java.util.Map;

public class ExpressionContext implements Expression, Context<Name, Expression> {
    private Map<Name, Expression> container = new HashMap<>();
    private Context<Name, Expression> parent;

    public ExpressionContext() {}

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
    public void bind(Name key, Expression value) {
        if (!this.rebind(key, value)) {
            this.newbind(key, value);
        }
    }

    @Override
    public boolean rebind(Name key, Expression value) {
        if (container.containsKey(key)) { //update key
            container.put(key, value);
            return true;
        } else if (parent != null) {
            return parent.rebind(key, value);
        } else {
            return false;
        }
    }

    @Override
    public Expression newbind(Name key, Expression value) {
        return container.put(key, value);
    }

    @Override
    public boolean contains(Name key) {
        return container.containsKey(key) || parent != null && parent.contains(key);
    }

    @Override
    public void setParent(Context<Name, Expression> parent) {
        this.parent = parent;
    }
}
