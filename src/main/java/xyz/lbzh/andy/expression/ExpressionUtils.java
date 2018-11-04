package xyz.lbzh.andy.expression;

import org.springframework.util.CollectionUtils;
import xyz.lbzh.andy.expression.ast.*;
import xyz.lbzh.andy.expression.runtime.*;
import xyz.lbzh.andy.expression.template.TemplateExpression;
import xyz.lbzh.andy.expression.template.XmlExpression;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

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

    public static boolean isComplex(Expression expression) {
        return expression instanceof ComplexExpression;
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

}
