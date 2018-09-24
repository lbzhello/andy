package xyz.lbzh.andy.core;

import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.ExpressionType;
import xyz.lbzh.andy.expression.support.StringExpression;
import xyz.lbzh.andy.expression.support.ValueExpression;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class Definition {
    private Definition() {}


    /**
     * 表示token前有空白字符
     */
    public static String SPACE = "SPACE_";

    public static final Expression NIL = ExpressionType.NIL;

    /**
     * 系统提供函数，用于定义其他函数/对象
     */
    public static Expression DEFINE = ExpressionType.DEFINE;

    /**
     * 系统提供函数，用于定义匿名对象
     */
    public static Expression LAMBDA = ExpressionType.LAMBDA;

    /**
     * 用于定义属性/字段
     */
    public static Expression PAIR = ExpressionType.PAIR;

    /**
     * End of file
     */
    public static final Expression EOF = ExpressionType.EOF;

    /**
     * Head of file
     */
    public static final Expression HOF = ExpressionType.HOF;

    private static final Set<Character> delimiters = new HashSet<>();

    //e.g. a + 2
    private static Map<String,Integer> operator = new HashMap<>();

    /**
     * key: the name of command
     * value: the param sizes the command takes
     */
    private static HashMap<String, Integer> command = new HashMap<>();

    static {
        delimiters.add(',');
        delimiters.add(';');
        delimiters.add('.');
        delimiters.add(':');
        delimiters.add('(');
        delimiters.add(')');
        delimiters.add('{');
        delimiters.add('}');
        delimiters.add('[');
        delimiters.add(']');
        delimiters.add('"');
        delimiters.add('/');
        delimiters.add('\'');
        delimiters.add('\\');
    }

    static {
        operator.put("=", 0);

        operator.put("||", 11);
        operator.put("&&", 12);

        operator.put("==", 21);
        operator.put(">", 21);
        operator.put(">=", 21);
        operator.put("<", 21);
        operator.put("<=", 21);

        operator.put("+", 31);
        operator.put("-",31);

        operator.put("*", 41);
        operator.put("/", 41);

//        operator.put("++", 51);
//        operator.put("--", 51);
//
//        operator.put("!", 1113);
        operator.put(".", 1314);

        operator.put("else", 1314);
    }

    static {
        command.put("++", 1);
        command.put("--", 1);
        command.put("!", 1);

        command.put("if", 2);
        command.put("for", 2);
    }

    public static final boolean isDelimiter(Character c){
        return delimiters.contains(c);
    }

    public static final void addDelimiter(Character c){
        delimiters.add(c);
    }

    public static final void removeDelimiter(Character c){
        delimiters.remove(c);
    }

    public static final boolean isOperator(String op) {
        return operator.keySet().contains(op);
    }

    public static final boolean isOperator(Object op) {
        return isOperator((String)op);
    }

    public static final boolean isCommand(String op) {
        return command.keySet().contains(op);
    }

    public static final boolean isCommand(Object op) {
        return isCommand((String)op);
    }

    /**
     * get  priority of the operator
     * @param op
     * @return
     */
    public static final int getPriority(String op) {
        return operator.getOrDefault(op,1);
    }

    public static final int getCommandSize(String op) {
        return command.getOrDefault(op, 1);
    }

    public static final int getCommandSize(Object op) {
        return getCommandSize((String) op);
    }

    /**
     *
     * @param op1
     * @param op2
     * @return
     *      0 -> getPriority(op1) = getPriority(op2)
     *      1 -> getPriority(op1) > getPriority(op2)
     *      -1 -> getPriority(op1) < getPriority(op2)
     */
    public static final int comparePriority(String op1, String op2) {
        return getPriority(op1) == getPriority(op2) ? 0 : getPriority(op1) > getPriority(op2) ? 1 : -1;
    }

}
