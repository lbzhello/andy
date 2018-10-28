package xyz.lbzh.andy.expression;

import xyz.lbzh.andy.expression.ast.*;
import xyz.lbzh.andy.expression.runtime.*;
import xyz.lbzh.andy.expression.template.TemplateExpression;

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

    public static SquareBracketExpression asSquareBracket(Expression expression) {
        return (SquareBracketExpression)expression;
    }

    public static boolean isComplex(Expression expression) {
        return expression instanceof ComplexExpression;
    }

    public static boolean isMethod(Expression expression) {
        return expression instanceof MethodExpression;
    }

    public static MethodExpression asMethod(Expression expression) {
        return (MethodExpression)expression;
    }

    public static StringExpression asString(Expression expression) {
        return (StringExpression)expression;
    }

    public static NumberExpression asNumber(Expression expression) {
        return (NumberExpression)expression;
    }

    public static boolean isNative(Expression expression) {
        return expression instanceof NativeExpression;
    }

    public static NativeExpression asNative(Expression expression) {
        return (NativeExpression)expression;
    }

    public static boolean isTemplate(Expression expression) {
        return expression instanceof TemplateExpression;
    }
}
