package xyz.lius.andy.expression;

import xyz.lius.andy.expression.ast.*;
import xyz.lius.andy.expression.operator.*;
import xyz.lius.andy.expression.template.LineExpression;
import xyz.lius.andy.expression.template.TemplateExpression;
import xyz.lius.andy.expression.template.XmlExpression;
import xyz.lius.andy.expression.template.XmlTagExpression;

/**
 * 用于提供各种表达式语法树
 */
public class ExpressionFactory extends NativeFactory {
    public static BracketExpression bracket(Expression... expressions) {
        return new BracketExpression(expressions);
    }

    public static BracketExpression roundBracket(Expression... expressions) {
        return new RoundBracketExpression(expressions);
    }

    public static SquareBracketExpression squareBracket(Expression...expressions) {
        return new SquareBracketExpression(expressions);
    }

    public static CurlyBracketExpression curlyBracket() {
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

    public static DelimiterExpression delimiter(String value) {
        return new DelimiterExpression(value);
    }

    public static DelimiterExpression delimiter(String value, int lineNumber) {
        return new DelimiterExpression(value, lineNumber);
    }

    public static Operator lambda(BracketExpression bracket, CurlyBracketExpression curlyBracket) {
        return new LambdaExpression(bracket, curlyBracket);
    }

    public static Expression define(Expression key, CurlyBracketExpression value) {
        //a{...} => a(){...}
        if (key.getName() == Name.NIL && key instanceof BracketExpression) { //it's a lambda
            return lambda((BracketExpression) key, value);
        }
        return new DefineExpression(key instanceof Name ? ExpressionFactory.roundBracket(key) : (BracketExpression)key, value);
    }

    public static Operator colon(Expression key, Expression value) {
        return new ColonExpression(key, value);
    }

    public static BracketExpression comma(Expression... expressions) {
        return new CommaExpression(expressions);
    }

    public static Operator point(Expression left, Expression right) {
        Operator pointExpression = new PointExpression();
        pointExpression.add(left);
        pointExpression.add(right);
        return pointExpression;
    }

    public static ComplexExpression complex(Context<Name, Expression> context) {
        return new ComplexExpression(context);
    }

    public static JavaObjectExpression javaObject(Object o) {
        return new JavaObjectExpression(o);
    }

    public static JavaMethodExpression javaMethod(Object methodObject, String methodName) {
        return new JavaMethodExpression(methodObject, methodName);
    }

    public static ArrayMethodInvoker arrayMethod(Expression methodObject, String methodName) {
        return new ArrayMethodInvoker(methodObject, methodName);
    }

    public static XmlExpression xml() {
        return new XmlExpression();
    }

    public static XmlTagExpression xmlTag() {
        return new XmlTagExpression();
    }

    public static LineExpression line() {
        return new LineExpression();
    }

    public static TemplateExpression template() {
        return new TemplateExpression();
    }

    public static ErrorExpression error(String message) {
        return new ErrorExpression(message);
    }

    public static ErrorExpression error(Expression expression, String message) {
        return new ErrorExpression(expression, message);
    }

    public static Expression iter(String value) {
        return new StringIterExpression(value);
    }

}
