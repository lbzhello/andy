package xyz.lius.andy.expression;

import xyz.lius.andy.core.Definition;
import xyz.lius.andy.expression.ast.*;
import xyz.lius.andy.expression.context.ExpressionContext;
import xyz.lius.andy.expression.operator.CommaExpression;
import xyz.lius.andy.expression.operator.JavaObject;
import xyz.lius.andy.expression.operator.LambdaExpression;
import xyz.lius.andy.expression.operator.PointExpression;
import xyz.lius.andy.expression.template.XmlExpression;
import xyz.lius.andy.util.Pair;

public class TypeCheck {
    public static boolean isReturn(Expression expression) {
        return expression instanceof ReturnValue;
    }

    public static ReturnValue asReturn(Expression rstValue) {
        return (ReturnValue) rstValue;
    }

    public static boolean hasError(Expression expression) {
        return expression instanceof ErrorExpression;
    }

    public static boolean isConstant(Expression expression) {
        return isSymbol(expression) || isNumber(expression) || isString(expression);
    }

    public static boolean isSymbol(Expression expression) {
        return expression instanceof IdentifierExpression;
    }

    public static boolean isNumber(Expression expression) {
        return expression instanceof NumberExpression;
    }

    public static boolean isString(Expression expression) {
        return expression instanceof StringExpression;
    }

    public static boolean isLambda(Expression expression) {
        return expression instanceof LambdaExpression;
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
        return (SquareBracketExpression) expression;
    }

    public static boolean isComma(Expression expression) {
        return expression instanceof CommaExpression;
    }

    public static boolean isArray(Expression expression) {
        return expression instanceof ArrayExpression;
    }

    public static ArrayExpression asArray(Expression expression) {
        return (ArrayExpression) expression;
    }

    public static boolean isComplex(Expression expression) {
        return expression instanceof Complex;
    }

    public static Complex asComplex(Expression expression) {
        return (Complex)expression;
    }

    public static boolean isJavaObject(Expression expression) {
        return expression instanceof JavaObject;
    }

    public static JavaObject asJavaObject(Expression expression) {
        return (JavaObject)expression;
    }

    public static StringExpression asString(Expression expression) {
        return (StringExpression) expression;
    }

    public static NumberExpression asNumber(Expression expression) {
        return (NumberExpression) expression;
    }

    public static boolean isXml(Expression expression) {
        return expression instanceof XmlExpression;
    }

    public static XmlExpression asXml(Expression expression) {
        return (XmlExpression) expression;
    }

    public static boolean isExpression(Object rst) {
        return rst instanceof Expression;
    }

    public static Expression asExpression(Object obj) {
        return (Expression)obj;
    }

}
