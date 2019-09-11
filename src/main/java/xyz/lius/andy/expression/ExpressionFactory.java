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
public class ExpressionFactory {
    public static BracketExpression bracket(Expression... expressions) {
        return new BracketExpression(expressions);
    }

    public static RoundBracketExpression roundBracket(Expression... expressions) {
        return new RoundBracketExpression(expressions);
    }

    public static SquareBracketExpression squareBracket(Expression...expressions) {
        return new SquareBracketExpression(expressions);
    }

    public static CurlyBracketExpression curlyBracket() {
        return new CurlyBracketExpression();
    }

    public static IdentifierExpression symbol(String value) {
        return new IdentifierExpression(value);
    }

    public static IdentifierExpression symbol(String value, int lineNumber) {
        return new IdentifierExpression(value, lineNumber);
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

    public static LambdaExpression lambda(BracketExpression bracket, CurlyBracketExpression curlyBracket) {
        LambdaExpression lambda = new LambdaExpression();
        lambda.add(bracket);
        lambda.add(curlyBracket);
        return lambda;
    }

    public static Expression define(Expression key, CurlyBracketExpression value) {
        //a{...} => a(){...}
        if (key.getName() == Name.NIL && key instanceof BracketExpression) { //it's a lambda
            return lambda((BracketExpression) key, value);
        }
        DefineExpression def = new DefineExpression();
        def.add(key instanceof Name ? ExpressionFactory.roundBracket(key) : (BracketExpression)key);
        def.add(value);
        return def;
    }

    public static Operator colon(Expression key, Expression value) {
        Operator colon = new ColonExpression();
        colon.add(key);
        colon.add(value);
        return colon;
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

    public static JavaObject javaObject(Object o) {
        return new JavaObject(o);
    }

    public static JavaMethod javaMethod(Object methodObject, String methodName) {
        return new JavaMethod(methodObject, methodName);
    }

    public static ArrayMethod arrayMethod(Expression methodObject, String methodName) {
        return new ArrayMethod(methodObject, methodName);
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
