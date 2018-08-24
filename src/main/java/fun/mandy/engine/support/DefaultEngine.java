package fun.mandy.engine.support;

import fun.mandy.core.Definition;
import fun.mandy.engine.Engine;
import fun.mandy.expression.Expression;
import fun.mandy.expression.support.EvalExpression;

import java.util.HashMap;
import java.util.Map;

public class DefaultEngine implements Engine {
    Map<Object, Object> container = new HashMap<>();

    @Override
    public Object build(Object expression) {
        if (!(expression instanceof EvalExpression)) {
            return null;
        }
        Expression name = ((EvalExpression) expression).head();
        if (name.equals(Definition.DEFINE)) { //e.g. (define expr1 expr2)

        } else if (name.equals(Definition.COLON)) {

        }
        return null;
    }

    @Override
    public Object eval(Object expression) {

        return null;
    }
}
