package xyz.lius.andy.expression;

import xyz.lius.andy.expression.ast.BracketExpression;
import xyz.lius.andy.expression.base.ReturnExpression;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Operator expression Supplier
 * 单例模式-枚举实现
 */
public enum  OperatorSupplier implements Function<String, BracketExpression> {
    INSTANCE;
    private Map<String, Supplier<BracketExpression>> container;
    private Supplier<BracketExpression> defaultSupplier;

    OperatorSupplier() {
        container = new HashMap<>();
        defaultSupplier = () -> ExpressionFactory.roundBracket();

        container.put(Operator.RETURN.name, () -> new ReturnExpression());
    }

    @Override
    public BracketExpression apply(String name) {
        return container.getOrDefault(name, defaultSupplier).get();
    }

    public boolean isBinary(String name) {
        return false;
    }

    private enum Operator implements Expression {
        RETURN("return", 1, 1);

        private String name;
        //运算符类型 1: 一元运算符 2: 二元运算符
        private int type;
        //对于一元运算符，表示操作数数量， 对于二元运算符，表示运算符优先级
        private int number;

        Operator(String name, int type, int number) {
            this.name = name;
            this.type = type;
            this.number = number;
        }

        @Override
        public Expression eval(Context<Name, Expression> context) {
            return this;
        }

    }
}
