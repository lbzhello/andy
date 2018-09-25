package xyz.lbzh.andy.expression;

import java.util.HashMap;
import java.util.Map;

public class ExpressionContext implements Expression, Context<Expression, Object> {
    private Map<Expression, Object> container = new HashMap<>();
    private Context<Expression, Object> parent = null;

    public ExpressionContext(Context<Expression, Object> parent) {
        this.parent = parent;
    }

    @Override
    public Object lookup(Expression key) {
        Object o = container.getOrDefault(key, null);
        if (o == null && this.parent != null) {
            o = parent.lookup(key);
        }
        return o;
    }

    @Override
    public ExpressionContext bind(Expression key, Object value) {
        this.container.put(key, value);
        return this;
    }
}
