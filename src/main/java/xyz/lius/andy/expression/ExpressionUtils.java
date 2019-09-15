package xyz.lius.andy.expression;

import xyz.lius.andy.core.Definition;
import xyz.lius.andy.expression.ast.CurlyBracketExpression;
import xyz.lius.andy.expression.ast.RoundBracketExpression;
import xyz.lius.andy.expression.ast.SquareBracketExpression;
import xyz.lius.andy.expression.context.ExpressionContext;
import xyz.lius.andy.expression.operator.PointExpression;
import xyz.lius.andy.expression.template.XmlExpression;
import xyz.lius.andy.util.Pair;

public class ExpressionUtils {

    /**
     * 将表达式连接成一个 list
     * @param expressions
     * @return
     */
    public static SquareBracketExpression link(Expression... expressions) {
        SquareBracketExpression squareBracket = ExpressionFactory.squareBracket();
        for (Expression expression : expressions) {
            if (TypeCheck.isSquareBracket(expression)) {
                squareBracket.add(TypeCheck.asSquareBracket(expression).toArray());
            } else {
                squareBracket.add(expression);
            }
        }
        return squareBracket;
    }

    /**
     * 打印表达式
     * @param expression
     */
    public static void print(Expression expression) {
        if (TypeCheck.hasError(expression)) {
            System.err.println(expression);
        } else {
            if (TypeCheck.isXml(expression)) { //format
                System.out.println(ExpressionUtils.formatXml(TypeCheck.asXml(expression)));
            } else {
                System.out.println(expression);
            }
        }
    }

    /**
     * 格式化 xml, 4 空格缩进
     * @param xmlExpression
     * @return
     */
    public static String formatXml(XmlExpression xmlExpression) {
        return xmlToString(xmlExpression, "");
    }

    /**
     * Xml 转 String
     * @param xml
     * @param indent 缩进
     * @return
     */
    private static String xmlToString(XmlExpression xml, String indent) {
        StringBuffer xmlStr = new StringBuffer();
        String indentInc = indent + "    "; //add 4 spaces
        if (!xml.getStartTag().isEmpty()) {
            xmlStr.append(indent + xml.getStartTag() + "\n");
        }
        for (Expression element : xml.getBody().toArray()) {
            if (TypeCheck.isXml(element)) {
                xmlStr.append(xmlToString(TypeCheck.asXml(element), indentInc));
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

    /**
     * 根据赋值表达式求出上下文变量
     * @param context 上下文
     * @param assign 赋值表达式键值对 e.g. name = "lbz"
     * @param flag true 表示新建一个变量，false 表示新建或更新变量
     * @return
     */
    public static Expression evalAssign(Context<Name, Expression> context, Pair<Expression, Expression> assign, boolean flag) {
        Context<Name, Expression> bindContext = context;
        Name name;
        Expression value;
        if (assign.getKey() instanceof RoundBracketExpression) { //lambda (f x) = ...
            RoundBracketExpression left = (RoundBracketExpression) assign.getKey();
            if  (assign.getValue() instanceof CurlyBracketExpression) { //define a function. e.g. f(x) = { x }
                CurlyBracketExpression right = (CurlyBracketExpression) assign.getValue();
                name = left.getName();
                Complex complex = right.eval(context);
                complex.setParameters(left.getParameters());
                value = complex;
            } else { //e.g. f(x) = x + 1
                name = left.getName();
                Complex complex = ExpressionFactory.complex(new ExpressionContext(context));
                complex.setParameters(left.getParameters());
                complex.setCodes(new Expression[]{assign.getValue()});
                value = complex;
            }
        } else if (assign.getKey() instanceof PointExpression) { //e.g. (. a b) = ...
            PointExpression left = (PointExpression) assign.getKey();
            Expression parent = left.get(0).eval(context);
            if (parent instanceof Complex) {
                bindContext = ((Complex) parent).getContext();
                name = left.get(1) instanceof RoundBracketExpression ? left.get(1).eval(context).getName() : left.get(1).getName();
                value = assign.getValue().eval(context);
            } else {
                return ExpressionFactory.error(parent, "Left value should be ComplexExpression");
            }
        } else { //e.g. f = x + 1
            name = assign.getKey().getName();
            value = assign.getValue().eval(context);
        }
        if (flag) {
            // 新建一个变量, 效率更高
            bindContext.add(name, value);
        } else {
            bindContext.bind(name, value);
        }
        return Definition.NIL;
    }
}
