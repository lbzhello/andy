package xyz.lius.andy.core;

import xyz.lius.andy.expression.ExpressionFactory;
import xyz.lius.andy.expression.ExpressionType;
import xyz.lius.andy.expression.Operator;
import xyz.lius.andy.expression.base.ReturnExpression;

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

        operator.put(OperatorSingleton.NIL, () -> ExpressionType.NIL);
        operator.put(OperatorSingleton.TRUE, () -> ExpressionType.TRUE);
        operator.put(OperatorSingleton.FALSE, () -> ExpressionType.FALSE);

        operator.put(OperatorSingleton.RETURN, () -> new ReturnExpression());
//        operator.put(OperatorSingleton.NEW, () -> new NewExpression());
//        operator.put(OperatorSingleton.IMPORT, () -> new ReturnExpression());
    }

    @Override
    public Operator apply(String name) {
        return operator.getOrDefault(name, defaultSupplier).get();
    }

}
