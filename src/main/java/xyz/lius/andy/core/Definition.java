package xyz.lius.andy.core;

import xyz.lius.andy.expression.*;
import xyz.lius.andy.compiler.tokenizer.Token;
import xyz.lius.andy.compiler.tokenizer.TokenFlag;
import xyz.lius.andy.expression.context.ExpressionContext;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

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

    //represent expression it self
    public static final Name SELF = ExpressionFactory.symbol("self");

    /**
     * 脚本文件所在的目录
     */
    public static Name FILE_PARENT = ExpressionFactory.symbol("FILE_PARENT");

    /**
     * End of file
     */
    public static final Token EOF = TokenFlag.EOF;

    /**
     * Head of file
     */
    public static final Token HOF = TokenFlag.HOF;

    private static final Map<Character, Token> delimiter = new HashMap<>();

    //运算符定义
    private static Operator operator = OperatorSingleton.getSingleton();

    private static Function<String, Addable<Expression>> operatorSupplier = OperatorSupplier.INSTANCE;

    private static Context<Name, Expression> CORE_CONTEXT = new ExpressionContext(null);

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
        CORE_CONTEXT.bind(ExpressionFactory.symbol("="), ExpressionFactory.assign());
        CORE_CONTEXT.bind(ExpressionFactory.symbol("+"), ExpressionFactory.plus());
        CORE_CONTEXT.bind(ExpressionFactory.symbol("-"), ExpressionFactory.minus());
        CORE_CONTEXT.bind(ExpressionFactory.symbol("*"), ExpressionFactory.Multiply());
        // '/' is delimiter so it will be parse earlier on tokenizer
        CORE_CONTEXT.bind(ExpressionFactory.symbol("/"), ExpressionFactory.divide());

        CORE_CONTEXT.bind(ExpressionFactory.symbol("||"), ExpressionFactory.or());
        CORE_CONTEXT.bind(ExpressionFactory.symbol("=="), ExpressionFactory.equal());
        CORE_CONTEXT.bind(ExpressionFactory.symbol("!="), ExpressionFactory.notEqual());
        CORE_CONTEXT.bind(ExpressionFactory.symbol(">"), ExpressionFactory.greaterThan());
        CORE_CONTEXT.bind(ExpressionFactory.symbol(">="), ExpressionFactory.greaterEqual());
        CORE_CONTEXT.bind(ExpressionFactory.symbol("<"), ExpressionFactory.lessThan());
        CORE_CONTEXT.bind(ExpressionFactory.symbol("<="), ExpressionFactory.lessEqual());

        CORE_CONTEXT.bind(ExpressionFactory.symbol("nil"), ExpressionType.NIL);

        CORE_CONTEXT.bind(ExpressionFactory.symbol("true"), ExpressionType.TRUE);
        CORE_CONTEXT.bind(ExpressionFactory.symbol("false"), ExpressionType.FALSE);

        CORE_CONTEXT.bind(ExpressionFactory.symbol("if"), ExpressionFactory.ifExpression());
        CORE_CONTEXT.bind(ExpressionFactory.symbol("for"), ExpressionFactory.forExpression());
        CORE_CONTEXT.bind(ExpressionFactory.symbol("return"), ExpressionFactory.returnExpression());

        CORE_CONTEXT.bind(ExpressionFactory.symbol("new"), ExpressionFactory.newExpression());
        CORE_CONTEXT.bind(ExpressionFactory.symbol("import"), ExpressionFactory.importExpression());

        CORE_CONTEXT.bind(ExpressionFactory.symbol("->"), ExpressionFactory.arrow());

        CORE_CONTEXT.bind(ExpressionFactory.symbol("print"), ExpressionFactory.print());
        CORE_CONTEXT.bind(ExpressionFactory.symbol("file") , ExpressionFactory.file());
    }

    public static final Context<Name, Expression> getCoreContext() {
        return CORE_CONTEXT;
    }

    public static final Token getDelimiter(Character character) {
        return delimiter.get(character);
    }

    public static final boolean isDelimiter(Character c) {
        return delimiter.containsKey(c);
    }

    public static final boolean isBinary(String op) {
        return operator.isBinary(op);
    }

    public static final boolean isBinary(Object op) {
        return isBinary(String.valueOf(op));
    }

    public static final boolean isUnary(String op) {
        return operator.isUnary(op);
    }

    public static final boolean isUnary(Object op) {
        return isUnary(String.valueOf(op));
    }

    public static final int getOperands(String op) {
        return operator.getOperands(op);
    }

    public static final int compare(String op1, String op2) {
        return operator.compare(op1, op2);
    }

    public static final Addable<Expression> getOperator(String op) {
        return operatorSupplier.apply(op);
    }

}
