package xyz.lius.andy.core;

import xyz.lius.andy.expression.Addable;
import xyz.lius.andy.expression.Expression;
import xyz.lius.andy.expression.ExpressionFactory;
import xyz.lius.andy.expression.base.ReturnExpression;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Operator expression Supplier
 * 单例模式-枚举实现
 */
public enum  OperatorSupplier implements Function<String, Addable<Expression>> {
    INSTANCE;
    private Map<String, Supplier<Addable<Expression>>> operator;
    private Supplier<Addable<Expression>> defaultSupplier;

    OperatorSupplier() {
        operator = new HashMap<>();
        defaultSupplier = () -> ExpressionFactory.roundBracket();

        operator.put(OperatorSingleton.RETURN, () -> new ReturnExpression());
    }

    @Override
    public Addable<Expression> apply(String name) {
        return operator.getOrDefault(name, defaultSupplier).get();
    }

}
