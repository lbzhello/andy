package fun.mandy.expression.support;

import fun.mandy.expression.Context;
import fun.mandy.expression.Expression;
import fun.mandy.expression.Name;
import fun.mandy.expression.Unit;

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
