package xyz.lius.andy.core;

import xyz.lius.andy.expression.ExpressionFactory;
import xyz.lius.andy.expression.ExpressionType;
import xyz.lius.andy.expression.Operator;
import xyz.lius.andy.expression.base.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Operator expression Supplier
 * 单例模式-枚举实现
 */
public enum  OperatorSupplier implements Function<String, Operator> {
    INSTANCE;
    private Map<String, Supplier<Operator>> operator;
    private Supplier<Operator> defaultSupplier;

    OperatorSupplier() {
        operator = new HashMap<>();
        defaultSupplier = () -> ExpressionFactory.roundBracket();

        //---------------- unary -----------------//
        operator.put(OperatorSingleton.NIL, () -> ExpressionType.NIL);
        operator.put(OperatorSingleton.TRUE, () -> ExpressionType.TRUE);
        operator.put(OperatorSingleton.FALSE, () -> ExpressionType.FALSE);

        operator.put(OperatorSingleton.RETURN, () -> new ReturnExpression());
        operator.put(OperatorSingleton.NEW, () -> new NewExpression());
        operator.put(OperatorSingleton.IMPORT, () -> new ImportExpression());

        operator.put(OperatorSingleton.AUTO_INC, () -> ExpressionType.NIL);
        operator.put(OperatorSingleton.AUTO_DEC, () -> ExpressionType.NIL);
        operator.put(OperatorSingleton.NOT, () -> ExpressionType.NIL);

        operator.put(OperatorSingleton.IF, () -> new IfExpression());
        operator.put(OperatorSingleton.FOR, () -> new ForExpression());

        //---------------- binary -----------------//
//        operator.put(OperatorSingleton.ARROW, () -> new ArrowExpression());
    }

    @Override
    public Operator apply(String name) {
        return operator.getOrDefault(name, defaultSupplier).get();
    }

}
