package xyz.lbzh.andy.expression;

import xyz.lbzh.andy.core.Definition;
import xyz.lbzh.andy.expression.function.*;
import xyz.lbzh.andy.expression.internal.ErrorExpression;
import xyz.lbzh.andy.expression.support.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ExpressionFactory {
    public static BracketExpression bracket(Expression... expressions) {
        return new BracketExpression(expressions);
    }

    public static BracketExpression roundBracket(Expression... expressions) {
//        if (expressions.length > 0) {
//            Expression name = expressions[0];
//            if (Definition.isBinary(name)) {
//                if (Objects.equals(name.toString(), "+")) {
//                    return new PlusExpression(expressions);
//                } else if (Objects.equals(name.toString(), "-")) {
//                    return new MinusExpression(expressions[1], expressions[2]);
//                } else if (Objects.equals(name.toString(), "*")) {
//                    return new MultiplyExpression(expressions);
//                } else if (Objects.equals(name.toString(), "/")) {
//                    return new DivideExpression(expressions[1], expressions[2]);
//                } else if (Objects.equals(name.toString(), "||")) {
//                    return new OrExpression(expressions[1], expressions[2]);
//                } else if (Objects.equals(name.toString(), "print")) {
//                    return new PrintExpression(expressions[1]);
//                }
//            }
//        }
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

    //************ functions *****************//
    public static ErrorExpression error(String message) {
        return new ErrorExpression(message);
    }

}
