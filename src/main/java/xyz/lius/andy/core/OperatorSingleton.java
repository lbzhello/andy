package xyz.lius.andy.core;

import java.util.HashMap;
import java.util.Map;

/**
 * 运算符定义接口
 * 单例模式--静态内部类
 */
public final class OperatorSingleton implements OperatorDefinition {
    //--------- Unary -----------//
    public static final String NIL = "nil";
    public static final String TRUE = "true";
    public static final String FALSE = "false";
    public static final String RETURN = "return";
    public static final String NEW = "new";
    public static final String IMPORT = "import";
    public static final String NOT = "!";
    public static final String AUTO_DEC = "--";
    public static final String AUTO_INC = "++";
    public static final String IF = "if";
    public static final String FOR = "for";

    //--------- Binary -----------//
    public static final String ARROW = "->";
    public static final String ASSIGN = "=";
    public static final String OR = "||";
    public static final String AND = "&&";

    public static final String EQUAL = "==";
    public static final String NOT_EQUAL = "!=";
    public static final String GT = ">";
    public static final String GE = ">=";
    public static final String LT = "<";
    public static final String LE = "<=";

    public static final String PLUS = "+";
    public static final String MINUS = "-";
    public static final String MUL = "*";
    public static final String DIV = "/";

    public static final String POINT = ".";
    public static final String ELSE = "else";


    private static final class SingletonHolder {
        private static final OperatorDefinition INSTANCE = new OperatorSingleton();
    }

    public static final OperatorDefinition getSingleton() {
        return SingletonHolder.INSTANCE;
    }

    private Map<String, Integer> unary;
    private Map<String, Integer> binary;

    private OperatorSingleton() {
        unary = new HashMap<>();
        unary.put(NIL, 0);
        unary.put(TRUE, 0);
        unary.put(FALSE, 0);

        unary.put(RETURN, 1);
        unary.put(NEW, 1);
        unary.put(IMPORT, 1);

        unary.put(AUTO_INC, 1);
        unary.put(AUTO_DEC, 1);
        unary.put(NOT, 1);

        unary.put(IF, 2);
        unary.put(FOR, 2);

        binary = new HashMap<>();
        binary.put(ARROW, -1);

        binary.put(ASSIGN, 0);

        binary.put(OR, 11);
        binary.put(AND, 12);

        binary.put(EQUAL, 21);
        binary.put(NOT_EQUAL, 21);
        binary.put(GT, 21);
        binary.put(GE, 21);
        binary.put(LT, 21);
        binary.put(LE, 21);

        binary.put(PLUS, 31);
        binary.put(MINUS, 31);
        binary.put(MUL, 41);
        binary.put(DIV, 41);

        binary.put(POINT, 1314);
        binary.put(ELSE, 1314);
    }

    @Override
    public boolean isUnary(String name) {
        return unary.containsKey(name);
    }

    @Override
    public boolean isBinary(String name) {
        return binary.containsKey(name);
    }

    //operator precedence
    @Override
    public int compare(String op1, String op2) {
        return binary.get(op1) - binary.get(op2);
    }

    @Override
    public int getOperands(String op) {
        return unary.getOrDefault(op, 0);
    }
}
