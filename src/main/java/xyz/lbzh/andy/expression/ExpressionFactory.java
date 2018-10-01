package xyz.lbzh.andy.expression;

import xyz.lbzh.andy.expression.support.*;

import java.util.Collections;
import java.util.List;

public class ExpressionFactory {
    public static BracketExpression bracket(Expression... expressions) {
        return new BracketExpression(expressions);
    }

    public static BracketExpression roundBracket(Expression... expressions) {
        return new RoundBracketExpression(expressions);
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

    public static BracketExpression define(Expression key, CurlyBracketExpression value) {
        //a{...} => a(){...}
        return RoundBracketExpression.define(key instanceof Name ? ExpressionFactory.roundBracket(key) : (BracketExpression)key, value);
    }

    public static BracketExpression pair(Expression key, Expression value) {
        return RoundBracketExpression.pair(key, value);
    }

    public static ComplexExpression complex(Context<Name, Object> context) {
        return new ComplexExpression(context);
    }

}
