package xyz.lbzh.andy.core;

import xyz.lbzh.andy.expression.*;
import xyz.lbzh.andy.expression.runtime.*;
import xyz.lbzh.andy.tokenizer.LineNumberToken;
import xyz.lbzh.andy.tokenizer.TokenFlag;

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
    public static final LineNumberToken EOF = TokenFlag.EOF;

    /**
     * Head of file
     */
    public static final LineNumberToken HOF = TokenFlag.HOF;

    private static final Set<Character> delimiters = new HashSet<>();

    //e.g. a + 2
    private static Map<String,Integer> binary = new HashMap<>();

    /**
     * key: the name of unary
     * value: Number Of Operands
     */
    private static HashMap<String, Integer> unary = new HashMap<>();

    private static Context<Name, Expression> CORE_CONTEXT = new ExpressionContext(null);

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
        binary.put("=", 0);

        binary.put("||", 11);
        binary.put("&&", 12);

        binary.put("==", 21);
        binary.put(">", 21);
        binary.put(">=", 21);
        binary.put("<", 21);
        binary.put("<=", 21);

        binary.put("+", 31);
        binary.put("-",31);

        binary.put("*", 41);
        binary.put("/", 41);

//        binary.put("++", 51);
//        binary.put("--", 51);
//
//        binary.put("!", 1113);
        binary.put(".", 1314);

        binary.put("else", 1314);
    }

    static {
        unary.put("++", 1);
        unary.put("--", 1);
        unary.put("!", 1);

        unary.put("if", 2);
        unary.put("for", 2);
    }

    static {
        CORE_CONTEXT.bind(ExpressionFactory.symbol("="), new AssignExpression());
        CORE_CONTEXT.bind(ExpressionFactory.symbol("+"), new PlusExpression());
        CORE_CONTEXT.bind(ExpressionFactory.symbol("-"), new MinusExpression());
        CORE_CONTEXT.bind(ExpressionFactory.symbol("*"), new MultiplyExpression());
        CORE_CONTEXT.bind(ExpressionFactory.symbol("/"), new DivideExpression());

        CORE_CONTEXT.bind(ExpressionFactory.symbol("."), new PointExpression());

        CORE_CONTEXT.bind(ExpressionFactory.symbol("||"), new OrExpression());


        CORE_CONTEXT.bind(ExpressionFactory.symbol("print"), new PrintExpression());
    }

    public static final Context<Name, Expression> getCoreContext() {
        return CORE_CONTEXT;
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

    public static final boolean isBinary(String op) {
        return binary.keySet().contains(op);
    }

    public static final boolean isBinary(Object op) {
        return isBinary(op.toString());
    }

    public static final boolean isUnary(String op) {
        return unary.keySet().contains(op);
    }

    public static final boolean isUnary(Object op) {
        return isUnary(op.toString());
    }

    /**
     * get  priority of the binary
     * @param op
     * @return
     */
    public static final int getPriority(String op) {
        return binary.getOrDefault(op,1);
    }

    public static final int getNumberOfOperands(String op) {
        return unary.getOrDefault(op, 1);
    }

    public static final int getNumberOfOperands(Object op) {
        return getNumberOfOperands((String) op);
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
