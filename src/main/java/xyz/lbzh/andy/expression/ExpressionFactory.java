package xyz.lbzh.andy.expression;

import xyz.lbzh.andy.expression.ast.*;
import xyz.lbzh.andy.expression.runtime.DefineExpression;
import xyz.lbzh.andy.expression.runtime.LambdaExpression;
import xyz.lbzh.andy.expression.runtime.PairExpression;
import xyz.lbzh.andy.expression.runtime.ComplexExpression;
import xyz.lbzh.andy.tokenizer.Token;

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

    public static TokenExpression token(Object token) {
        return new TokenExpression(token);
    }

    public static TokenExpression token(Object token, int lineNumber) {
        return new TokenExpression(token, lineNumber);
    }

    public static SymbolExpression symbol(String value) {
        return new SymbolExpression(value);
    }

    public static SymbolExpression symbol(String value, int lineNumber) {
        return new SymbolExpression(value, lineNumber);
    }

    public static StringExpression string(String value) {
        return new StringExpression(value);
    }

    public static StringExpression string(String value, int lineNumber) {
        return new StringExpression(value, lineNumber);
    }

    public static NumberExpression number(String val) {
        return new NumberExpression(val);
    }

    public static NumberExpression number(String val, int lineNumber) {
        return new NumberExpression(val, lineNumber);
    }

    public static NumberExpression number(double val) {
        return new NumberExpression(val);
    }

    public static NumberExpression number(double val, int lineNumber) {
        return new NumberExpression(val, lineNumber);
    }

    public static BracketExpression lambda(BracketExpression bracket, CurlyBracketExpression curlyBracket) {
        return new LambdaExpression(bracket, curlyBracket);
    }

    public static BracketExpression define(Expression key, CurlyBracketExpression value) {
        //a{...} => a(){...}
        if (key.getName() == Name.NIL && key instanceof BracketExpression) { //it's a lambda
            return lambda((BracketExpression) key, value);
        }
        return new DefineExpression(key instanceof Name ? ExpressionFactory.roundBracket(key) : (BracketExpression)key, value);
    }

    public static BracketExpression pair(Expression key, Expression value) {
        return new PairExpression(key, value);
    }

    public static ComplexExpression complex(Context<Name, Expression> context) {
        return new ComplexExpression(context);
    }

    public static ErrorExpression error(String message) {
        return new ErrorExpression(message);
    }

    public static ErrorExpression error(Expression expression, String message) {
        return new ErrorExpression(expression, message);
    }


}
