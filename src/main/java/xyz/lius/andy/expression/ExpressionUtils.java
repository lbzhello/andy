package xyz.lius.andy.expression;

import xyz.lius.andy.expression.ast.*;
import xyz.lius.andy.expression.runtime.*;
import xyz.lius.andy.expression.template.TemplateExpression;
import xyz.lius.andy.expression.template.XmlExpression;

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

    public static SymbolExpression asSymbol(Expression expression) {
        return (SymbolExpression) expression;
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

    public static BracketExpression asBracket(Expression expression) {
        return (BracketExpression)expression;
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

    public static SquareBracketExpression pair(Expression... expressions) {
        SquareBracketExpression squareBracket = ExpressionFactory.squareBracket();
        for (Expression expression : expressions) {
            if (isSquareBracket(expression)) {
                squareBracket.list().addAll(asSquareBracket(expression).list());
            } else {
                squareBracket.add(expression);
            }
        }
        return squareBracket;
    }

    public static boolean isArray(Expression expression) {
        return expression instanceof ExpressionArray;
    }

    public static ExpressionArray asArray(Expression expression) {
        return (ExpressionArray) expression;
    }

    public static boolean isComplex(Expression expression) {
        return expression instanceof ComplexExpression;
    }

    public static ComplexExpression asComplex(Expression expression) {
        return (ComplexExpression)expression;
    }

    public static boolean isObject(Expression expression) {
        return expression instanceof ObjectExpression;
    }

    public static ObjectExpression asObject(Expression expression) {
        return (ObjectExpression)expression;
    }

    public static boolean isMethod(Expression expression) {
        return expression instanceof MethodExpression;
    }

    public static MethodExpression asMethod(Expression expression) {
        return (MethodExpression) expression;
    }

    public static StringExpression asString(Expression expression) {
        return (StringExpression) expression;
    }

    public static NumberExpression asNumber(Expression expression) {
        return (NumberExpression) expression;
    }

    public static boolean isNative(Expression expression) {
        return expression instanceof NativeExpression;
    }

    public static NativeExpression asNative(Expression expression) {
        return (NativeExpression) expression;
    }

    public static boolean isXml(Expression expression) {
        return expression instanceof XmlExpression;
    }

    public static XmlExpression asXml(Expression expression) {
        return (XmlExpression) expression;
    }

    public static boolean isTemplate(Expression expression) {
        return expression instanceof TemplateExpression;
    }

    public static String formatXml(XmlExpression xmlExpression) {
        return xmlToString(xmlExpression, "");
    }

    private static String xmlToString(XmlExpression xml, String indent) {
        StringBuffer xmlStr = new StringBuffer();
        String indentInc = indent + "    "; //add 4 spaces
        if (!xml.getStartTag().isEmpty()) {
            xmlStr.append(indent + xml.getStartTag() + "\n");
        }
        for (Expression element : xml.getBody().list()) {
            if (ExpressionUtils.isXml(element)) {
                xmlStr.append(xmlToString(ExpressionUtils.asXml(element), indentInc));
            } else {
                if (element.toString().trim().length() != 0) {
                    xmlStr.append(indentInc + element + "\n");
                }
            }
        }
        if (!xml.getCloseTag().isEmpty()) {
            xmlStr.append(indent + xml.getCloseTag() + "\n");
        }

        return xmlStr.toString();
    }

    public static boolean isExpression(Object rst) {
        return rst instanceof Expression;
    }

    public static Expression asExpression(Object obj) {
        return (Expression)obj;
    }
}