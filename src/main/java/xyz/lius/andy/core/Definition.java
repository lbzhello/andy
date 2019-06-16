package xyz.lius.andy.core;

import xyz.lius.andy.compiler.tokenizer.Token;
import xyz.lius.andy.compiler.tokenizer.TokenFlag;
import xyz.lius.andy.expression.*;
import xyz.lius.andy.expression.context.ExpressionContext;

import java.util.HashMap;
import java.util.Map;
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

    //运算符定义
    private static OperatorDefinition operatorDefinition = OperatorSingleton.getSingleton();

    private static Function<String, Operator> operatorSupplier = OperatorSupplier.INSTANCE;

    public static void setOperatorDefinition(OperatorDefinition definition, Function<String, Operator> supplier) {
        operatorDefinition = definition;
        operatorSupplier = supplier;
    }

    public static final boolean isBinary(Expression op) {
        return operatorDefinition.isBinary(String.valueOf(op));
    }

    public static final boolean isUnary(Expression op) {
        return operatorDefinition.isUnary(String.valueOf(op));
    }

    public static final int getOperands(Expression op) {
        return operatorDefinition.getOperands(String.valueOf(op));
    }

    public static final int compare(Expression op1, Expression op2) {
        return operatorDefinition.compare(String.valueOf(op1), String.valueOf(op2));
    }

    public static final Operator getOperator(Expression op) {
        return operatorSupplier.apply(String.valueOf(op));
    }

}
