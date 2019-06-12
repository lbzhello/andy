package xyz.lius.andy.expression;

import xyz.lius.andy.core.Constant;
import xyz.lius.andy.expression.ast.BracketExpression;
import xyz.lius.andy.expression.base.ReturnExpression;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * BaseBracketExpression Supplier
 * 单例模式-静态内部类
 */
public class BaseSupplier implements Function<String, BracketExpression> {

    private Map<String, Supplier<BracketExpression>> container;
    private Supplier<BracketExpression> defaultSupplier;

    private BaseSupplier() {
        this.container = new HashMap<>();
        defaultSupplier = () -> ExpressionFactory.roundBracket();
    }

    private static final class SingletonHolder {
        private static final BaseSupplier INSTANCE = new BaseSupplier();
        static {
            INSTANCE.container.put(Constant.RETURN, () -> new ReturnExpression());
        }
    }

    public static BaseSupplier getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public BracketExpression apply(String name) {
        return container.getOrDefault(name, defaultSupplier).get();
    }
}
