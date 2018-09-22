package xyz.lbzh.andy.expression.support;

import xyz.lbzh.andy.expression.Context;
import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.Name;
import xyz.lbzh.andy.expression.Unit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultContext implements Context<Name,Expression> {

    private Map<Name,Expression> container = new HashMap<>();

    public DefaultContext(){}

    @Override
    public Expression lookup(Name key) {
        return container.get(key);
    }

    @Override
    public Expression bind(Name key, Expression value) {
        return container.put(key, value);
    }
}
