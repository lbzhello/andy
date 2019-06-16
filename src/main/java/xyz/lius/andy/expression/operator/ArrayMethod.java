package xyz.lius.andy.expression.operator;

import xyz.lius.andy.expression.*;
import xyz.lius.andy.util.AbstractContainer;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.HashMap;
import java.util.Map;

/**
 * 数组方法调用缓存, 比调用 JavaMethod 速度要快一点
 * @see JavaMethod
 */
public class ArrayMethod extends AbstractContainer implements Operator {
    //key: methodName value: methodHandle
    private static Map<String, MethodHandle> cachedMethod = new HashMap<>();

    static {
        try {
            cachedMethod.put("map", mapMethod());
            cachedMethod.put("each", eachMethod());
            cachedMethod.put("filter", filterMethod());
            cachedMethod.put("mapValues", mapValuesMethod());
            cachedMethod.put("reduce", reduceMethod());
            cachedMethod.put("reduceByKey", reduceByKeyMethod());

            cachedMethod.put("groupBy", groupByMethod());
            cachedMethod.put("groupByKey", groupByKeyMethod());

            cachedMethod.put("first", firstMethod());
            cachedMethod.put("other", otherMethod());
            cachedMethod.put("reverse", reverseMethod());
            cachedMethod.put("count", countMethod());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    //当前选中的方法句柄
    private MethodHandle methodHandle;
    public ArrayMethod(Expression methodObject, String methodName) {
        methodHandle = cachedMethod.get(methodName);
        if (methodHandle == null) {
            ExpressionFactory.error(methodObject, "No such method:" + methodName);
            throw  new RuntimeException("No such method:" + methodName);
        } else {
            methodHandle = methodHandle.bindTo(methodObject);
        }
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        try {
            return  (Expression)methodHandle.asSpreader(Object[].class, size()).invoke(toArray());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return ExpressionFactory.error(this, "Illegal method type or length!");
    }

    private static MethodHandle mapMethod() throws NoSuchMethodException, IllegalAccessException {
        return MethodHandles.lookup().findVirtual(ArrayExpression.class, "map",
                MethodType.methodType(Expression.class, Expression.class));
    }

    private static MethodHandle eachMethod() throws NoSuchMethodException, IllegalAccessException {
        return MethodHandles.lookup().findVirtual(ArrayExpression.class, "each",
                MethodType.methodType(Expression.class, Expression.class));
    }

    private static MethodHandle filterMethod() throws NoSuchMethodException, IllegalAccessException {
        return MethodHandles.lookup().findVirtual(ArrayExpression.class, "filter",
                MethodType.methodType(Expression.class, Expression.class));
    }

    private static MethodHandle mapValuesMethod() throws NoSuchMethodException, IllegalAccessException {
        return MethodHandles.lookup().findVirtual(ArrayExpression.class, "mapValues",
                MethodType.methodType(Expression.class, Expression.class));
    }

    private static MethodHandle reduceMethod() throws NoSuchMethodException, IllegalAccessException {
        return MethodHandles.lookup().findVirtual(ArrayExpression.class, "reduce",
                MethodType.methodType(Expression.class, Expression.class));
    }

    private static MethodHandle reduceByKeyMethod() throws NoSuchMethodException, IllegalAccessException {
        return MethodHandles.lookup().findVirtual(ArrayExpression.class, "reduceByKey",
                MethodType.methodType(Expression.class, Expression.class));
    }

    private static MethodHandle groupByMethod() throws NoSuchMethodException, IllegalAccessException {
        return MethodHandles.lookup().findVirtual(ArrayExpression.class, "groupBy",
                MethodType.methodType(Expression.class, Expression.class));
    }

    private static MethodHandle groupByKeyMethod() throws NoSuchMethodException, IllegalAccessException {
        return MethodHandles.lookup().findVirtual(ArrayExpression.class, "groupByKey",
                MethodType.methodType(Expression.class));
    }

    private static MethodHandle firstMethod() throws NoSuchMethodException, IllegalAccessException {
        return MethodHandles.lookup().findVirtual(ArrayExpression.class, "first",
                MethodType.methodType(Expression.class));
    }

    private static MethodHandle otherMethod() throws NoSuchMethodException, IllegalAccessException {
        return MethodHandles.lookup().findVirtual(ArrayExpression.class, "other",
                MethodType.methodType(Expression.class));
    }

    private static MethodHandle reverseMethod() throws NoSuchMethodException, IllegalAccessException {
        return MethodHandles.lookup().findVirtual(ArrayExpression.class, "reverse",
                MethodType.methodType(Expression.class));
    }

    private static MethodHandle countMethod() throws NoSuchMethodException, IllegalAccessException {
        return MethodHandles.lookup().findVirtual(ArrayExpression.class, "count",
                MethodType.methodType(Expression.class));
    }
}
