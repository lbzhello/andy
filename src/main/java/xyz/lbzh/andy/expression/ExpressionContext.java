package xyz.lbzh.andy.expression;

import java.util.HashMap;
import java.util.Map;

public class ExpressionContext implements Expression, Context<Name, Object> {
    private Map<Name, Object> container = new HashMap<>();
    private Context<Name, Object> parent = null;

    public ExpressionContext(Context<Name, Object> parent) {
        this.parent = parent;
    }

    @Override
    public Object lookup(Name key) {
        Object o = container.getOrDefault(key, null);
        if (o == null && this.parent != null) {
            o = parent.lookup(key);
        }
        return o;
    }

    @Override
    public ExpressionContext bind(Name key, Object value) {
        this.container.put(key, value);
        return this;
    }

}
