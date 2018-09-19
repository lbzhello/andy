package fun.mandy.engine.support;

import fun.mandy.core.Definition;
import fun.mandy.engine.Engine;
import fun.mandy.expression.Expression;
import fun.mandy.expression.support.SExpression;

import java.util.HashMap;
import java.util.Map;

public class DefaultEngine implements Engine {
    Map<Object, Object> container = new HashMap<>();

    @Override
    public Object build(Object expression) {
        return null;
    }

    @Override
    public Object eval(Object expression) {

        return null;
    }
}
