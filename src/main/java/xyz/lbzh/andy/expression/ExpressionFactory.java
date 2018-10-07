package xyz.lbzh.andy.expression;

import xyz.lbzh.andy.expression.function.ReturnExpression;
import xyz.lbzh.andy.expression.support.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ExpressionFactory {
    public static BracketExpression bracket(Expression... expressions) {
        return new BracketExpression(expressions);
    }

    public static BracketExpression roundBracket(Expression... expressions) {
        if (expressions.length >= 1 && false) {
            return functionMux(expressions[0]);
        }
        return new RoundBracketExpression(expressions);
    }

    private static BracketExpression functionMux(Expression expression) {
        if (Objects.equals(expression.toString(), "+")) {

        }
        return null;
    }

    public static BracketExpression squareBracket(Expression...expressions) {
        return new SquareBracketExpression(expressions);
    }

    public static CurlyBracketExpression curlyBracket(Expression... expressions) {
        return new CurlyBracketExpression();
    }

    public static BracketExpression operator(Expression... expressions) {
        return RoundBracketExpression.operator(expressions);
    }

    public static BracketExpression lambda(BracketExpression bracket, CurlyBracketExpression curlyBracket) {
        return RoundBracketExpression.lambda(bracket, curlyBracket);
    }

    public static BracketExpression define(Expression key, CurlyBracketExpression value) {
        //a{...} => a(){...}
        if (key.getName() == Name.NIL && key instanceof BracketExpression) { //it's a lambda
            return lambda((BracketExpression) key, value);
        }
        return RoundBracketExpression.define(key instanceof Name ? ExpressionFactory.roundBracket(key) : (BracketExpression)key, value);
    }

    public static BracketExpression pair(Expression key, Expression value) {
        return RoundBracketExpression.pair(key, value);
    }

    public static ComplexExpression complex(Context<Name, Expression> context) {
        return new ComplexExpression(context);
    }

}
