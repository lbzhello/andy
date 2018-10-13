package xyz.lbzh.andy.expression;

import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.support.NumberExpression;
import xyz.lbzh.andy.expression.support.StringExpression;
import xyz.lbzh.andy.expression.support.SymbolExpression;

import java.io.*;

public class ExpressionUtils {
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
}
