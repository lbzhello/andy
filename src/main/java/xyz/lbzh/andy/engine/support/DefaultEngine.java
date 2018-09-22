package xyz.lbzh.andy.engine.support;

import xyz.lbzh.andy.core.Definition;
import xyz.lbzh.andy.engine.Engine;
import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.support.SExpression;

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
