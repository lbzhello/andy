package xyz.lbzh.andy.core;

import xyz.lbzh.andy.expression.*;
import xyz.lbzh.andy.tokenizer.Token;
import xyz.lbzh.andy.tokenizer.TokenFlag;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class Definition {
    private Definition() {
    }

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
    public static final Token EOF = TokenFlag.EOF;

    /**
     * Head of file
     */
    public static final Token HOF = TokenFlag.HOF;

    private static final Set<Character> delimiters = new HashSet<>();

    private static final Map<Character, Token> delimiter = new HashMap<>();

    //e.g. a + 2
    private static Map<String, Integer> binary = new HashMap<>();

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
//        delimiters.add('/');
        delimiters.add('\'');
        delimiters.add('\\');

//        delimiters.add('!');
//        delimiters.add('=');
//        delimiters.add('<');
//        delimiters.add('>');
    }

    static {
        delimiter.put(',', TokenFlag.COMMA);
        delimiter.put(';', TokenFlag.SEMICOLON);
        delimiter.put('.', TokenFlag.POINT);
        delimiter.put(':', TokenFlag.COLON);
        delimiter.put('(', TokenFlag.ROUND_BRACKET_LEFT);
        delimiter.put(')', TokenFlag.ROUND_BRACKET_RIGHT);
        delimiter.put('{', TokenFlag.CURLY_BRACKET_LEFT);
        delimiter.put('}', TokenFlag.CURLY_BRACKET_RIGHT);
        delimiter.put('[', TokenFlag.SQUARE_BRACKET_LEFT);
        delimiter.put(']', TokenFlag.SQUARE_BRACKET_RIGHT);
//        delimiter.put('/', TokenFlag.SLASH_RIGHT);
        delimiter.put('\\',TokenFlag.SLASH_LEFT);
        delimiter.put('"', TokenFlag.QUOTE_MARK_DOUBLE);
        delimiter.put('\'',TokenFlag.QUOTE_MARK_SINGLE);

//        delimiter.put('!', ExpressionFactory.symbol("!"));
//        delimiter.put('=', ExpressionFactory.symbol("="));
//        delimiter.put('<', ExpressionFactory.symbol("<"));
//        delimiter.put('>', ExpressionFactory.symbol(">"));
    }

    static {
        binary.put("->", -1);

        binary.put("=", 0);

        binary.put("||", 11);
        binary.put("&&", 12);

        binary.put("==", 21);
        binary.put("!=", 21);
        binary.put(">", 21);
        binary.put(">=", 21);
        binary.put("<", 21);
        binary.put("<=", 21);

        binary.put("+", 31);
        binary.put("-", 31);

        binary.put("*", 41);

        binary.put("/", 41);
//        binary.put(TokenFlag.SLASH_RIGHT.toString(), 41); //equal to '/'

        binary.put(".", 1314);

        binary.put("else", 1314);
    }

    static {
        unary.put("nil", 0);
        unary.put("true", 0);
        unary.put("false", 0);

        unary.put("++", 1);
        unary.put("--", 1);
        unary.put("!", 1);

        unary.put("return", 1);

        unary.put("if", 2);
        unary.put("for", 2);
    }

    static {
        CORE_CONTEXT.bind(ExpressionFactory.symbol("="), ExpressionFactory.getExpression("="));
        CORE_CONTEXT.bind(ExpressionFactory.symbol("+"), ExpressionFactory.getExpression("+"));
        CORE_CONTEXT.bind(ExpressionFactory.symbol("-"), ExpressionFactory.getExpression("-"));
        CORE_CONTEXT.bind(ExpressionFactory.symbol("*"), ExpressionFactory.getExpression("*"));
        // '/' is delimiter so it will be parse earlier on tokenizer
        CORE_CONTEXT.bind(ExpressionFactory.symbol("/"), ExpressionFactory.getExpression("/"));

        CORE_CONTEXT.bind(ExpressionFactory.symbol("||"), ExpressionFactory.getExpression("||"));
        CORE_CONTEXT.bind(ExpressionFactory.symbol("=="), ExpressionFactory.getExpression("=="));
        CORE_CONTEXT.bind(ExpressionFactory.symbol("!="), ExpressionFactory.getExpression("!="));
        CORE_CONTEXT.bind(ExpressionFactory.symbol(">"), ExpressionFactory.getExpression(">"));
        CORE_CONTEXT.bind(ExpressionFactory.symbol(">="), ExpressionFactory.getExpression(">="));
        CORE_CONTEXT.bind(ExpressionFactory.symbol("<"), ExpressionFactory.getExpression("<"));
        CORE_CONTEXT.bind(ExpressionFactory.symbol("<="), ExpressionFactory.getExpression("<="));

        CORE_CONTEXT.bind(ExpressionFactory.symbol("nil"), ExpressionType.NIL);

        CORE_CONTEXT.bind(ExpressionFactory.symbol("true"), ExpressionType.TRUE);
        CORE_CONTEXT.bind(ExpressionFactory.symbol("false"), ExpressionType.FALSE);

        CORE_CONTEXT.bind(ExpressionFactory.symbol("if"), ExpressionFactory.getExpression("if"));
        CORE_CONTEXT.bind(ExpressionFactory.symbol("for"), ExpressionFactory.getExpression("for"));

        CORE_CONTEXT.bind(ExpressionFactory.symbol("return"), ExpressionFactory.getExpression("return"));

        CORE_CONTEXT.bind(ExpressionFactory.symbol("print"), ExpressionFactory.getExpression("print"));

        CORE_CONTEXT.bind(ExpressionFactory.symbol("->"), ExpressionFactory.getExpression("->"));
    }

    public static final Context<Name, Expression> getCoreContext() {
        return CORE_CONTEXT;
    }

    public static final Token getDelimiter(Character character) {
        return delimiter.get(character);
    }

    public static final boolean isDelimiter(Character c) {
        return delimiters.contains(c);
    }

    public static final void addDelimiter(Character c) {
        delimiters.add(c);
    }

    public static final void removeDelimiter(Character c) {
        delimiters.remove(c);
    }

    public static final boolean isBinary(String op) {
        return binary.keySet().contains(op);
    }

    public static final boolean isBinary(Object op) {
        return isBinary(String.valueOf(op));
    }

    public static final boolean isUnary(String op) {
        return unary.keySet().contains(op);
    }

    public static final boolean isUnary(Object op) {
        return isUnary(String.valueOf(op));
    }

    /**
     * get  priority of the binary
     *
     * @param op
     * @return
     */
    public static final int getPriority(String op) {
        return binary.getOrDefault(op, 1);
    }

    public static final int getNumberOfOperands(String op) {
        return unary.getOrDefault(op, 1);
    }

    public static final int getNumberOfOperands(Object op) {
        return getNumberOfOperands(String.valueOf(op));
    }

    /**
     * @param op1
     * @param op2
     * @return 0 -> getPriority(op1) = getPriority(op2)
     * 1 -> getPriority(op1) > getPriority(op2)
     * -1 -> getPriority(op1) < getPriority(op2)
     */
    public static final int comparePriority(String op1, String op2) {
        return getPriority(op1) == getPriority(op2) ? 0 : getPriority(op1) > getPriority(op2) ? 1 : -1;
    }

}
