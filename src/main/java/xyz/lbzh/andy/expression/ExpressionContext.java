package xyz.lbzh.andy.expression;

import java.util.HashMap;
import java.util.Map;

public class ExpressionContext implements Expression, Context<Expression, Object> {
    private Map<Expression, Object> container = new HashMap<>();

    @Override
    public Object lookup(Expression key) {
        return container.getOrDefault(key, ExpressionType.NIL);
    }

    @Override
    public ExpressionContext bind(Expression key, Object value) {
        this.container.put(key, value);
        return this;
    }
}