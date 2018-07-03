package fun.mandy.core;

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

    /**
     * 系统提供函数，用于定义其他函数/对象
     */
    public static String DEFINE = "define";

    /**
     * 用于定义属性/字段
     */
    public static String DEFINE_FIELD = ":";

    private static final Set<Character> delimiters = new HashSet<>();

    private static Map<String,Integer> operator = new HashMap<>();

    static {
        delimiters.add(',');
        delimiters.add(';');
        delimiters.add('.');
        delimiters.add(':');
        delimiters.add('(');
        delimiters.add(')');
        delimiters.add('{');
        delimiters.add('}');
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
        operator.put(">=", 21);
        operator.put("<=", 21);

        operator.put("+", 31);
        operator.put("-",31);

        operator.put("*", 41);
        operator.put("/", 41);

        operator.put("++", 51);
        operator.put("--", 51);

        operator.put("!", 1113);
        operator.put(".", 1314);
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

    public static final int getPriority(String op) {
        return operator.getOrDefault(op,1);
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
    public static final int getPriority(String op1, String op2) {
        return getPriority(op1) == getPriority(op2) ? 0 : getPriority(op1) > getPriority(op2) ? 1 : -1;
    }

}
