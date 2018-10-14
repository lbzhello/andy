package xyz.lbzh.andy.expression;

import xyz.lbzh.andy.expression.ast.*;
import xyz.lbzh.andy.expression.runtime.LambdaExpression;
import xyz.lbzh.andy.expression.runtime.ReturnExpression;

public class ExpressionUtils {
    public static boolean isReturn(Expression expression) {
        return expression instanceof ReturnExpression;
    }

    public static boolean hasError(Expression expression) {
        return expression instanceof ErrorExpression;
    }

    public static boolean isSymbol(Expression expression) {
        return expression instanceof SymbolExpression;
    }

    public static boolean isNumber(Expression expression) {
        return expression instanceof NumberExpression;
    }

    public static boolean isString(Expression expression) {
        return expression instanceof StringExpression;
    }

    public static boolean isValue(Expression expression) {
        return isNumber(expression) || isString(expression);
    }

    public static boolean isLambda(Expression expression) {
        return expression instanceof LambdaExpression;
    }

    public static boolean isBracket(Expression expression) {
        return expression instanceof BracketExpression;
    }

    public static boolean isRoundBracket(Expression expression) {
        return expression instanceof RoundBracketExpression;
    }

    public static boolean isCurlyBracket(Expression expression) {
        return expression instanceof CurlyBracketExpression;
    }

    public static boolean isSquareBracket(Expression expression) {
        return expression instanceof SquareBracketExpression;
    }
}
