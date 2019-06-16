package xyz.lius.andy.core;

import xyz.lius.andy.compiler.tokenizer.Token;
import xyz.lius.andy.expression.Expression;
import xyz.lius.andy.expression.ExpressionFactory;
import xyz.lius.andy.expression.Name;
import xyz.lius.andy.expression.Operator;

import java.util.function.Function;

public final class Definition {
    private Definition() {
    }

    public static final Operator NIL = Operator.NIL;

    public static final Operator TRUE = Operator.TRUE;

    public static final Operator FALSE = Operator.FALSE;

    //represent expression it self
    public static final Name SELF = ExpressionFactory.symbol("self");

    /**
     * 脚本文件所在的目录
     */
    public static Name FILE_PARENT = ExpressionFactory.symbol("FILE_PARENT");

    /**
     * End of file
     */
    public static final Token EOF = Token.EOF;

    /**
     * Head of file
     */
    public static final Token HOF = Token.HOF;

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
