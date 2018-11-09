package xyz.lbzh.andy.expression.runtime;

import xyz.lbzh.andy.expression.*;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.*;

/**
 * 数组方法调用缓存, 比调用 MethodExpression速度要快一点
 * @see MethodExpression
 */
public class ArrayMethodExpression extends NativeExpression {
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
    private Expression methodObject;
    public ArrayMethodExpression(Expression methodObject, String methodName) {
        methodHandle = cachedMethod.get(methodName);
        this.methodObject = methodObject;
        //先绑定后面调用asSpreader会报错,生成了一个新的对象？
//        methodHandle.bindTo(methodObject);
        if (methodHandle == null) {
            ExpressionFactory.error(methodObject, "No such method:" + methodName);
        }
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        try {
            List<Expression> parameters = new ArrayList<>(list().size());
            for (Expression element : list()) {
                parameters.add(element.eval(context));
            }
            return  (Expression)methodHandle.asSpreader(Object[].class, list().size()).bindTo(methodObject).invoke(parameters.toArray());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return ExpressionFactory.error(this, "Illegal method type or length!");
    }

    private static MethodHandle mapMethod() throws NoSuchMethodException, IllegalAccessException {
        return MethodHandles.lookup().findVirtual(ExpressionArray.class, "map",
                MethodType.methodType(Expression.class, Expression.class));
    }

    private static MethodHandle eachMethod() throws NoSuchMethodException, IllegalAccessException {
        return MethodHandles.lookup().findVirtual(ExpressionArray.class, "each",
                MethodType.methodType(Expression.class, Expression.class));
    }

    private static MethodHandle filterMethod() throws NoSuchMethodException, IllegalAccessException {
        return MethodHandles.lookup().findVirtual(ExpressionArray.class, "filter",
                MethodType.methodType(Expression.class, Expression.class));
    }

    private static MethodHandle mapValuesMethod() throws NoSuchMethodException, IllegalAccessException {
        return MethodHandles.lookup().findVirtual(ExpressionArray.class, "mapValues",
                MethodType.methodType(Expression.class, Expression.class));
    }

    private static MethodHandle reduceMethod() throws NoSuchMethodException, IllegalAccessException {
        return MethodHandles.lookup().findVirtual(ExpressionArray.class, "reduce",
                MethodType.methodType(Expression.class, Expression.class));
    }

    private static MethodHandle reduceByKeyMethod() throws NoSuchMethodException, IllegalAccessException {
        return MethodHandles.lookup().findVirtual(ExpressionArray.class, "reduceByKey",
                MethodType.methodType(Expression.class, Expression.class));
    }

    private static MethodHandle groupByMethod() throws NoSuchMethodException, IllegalAccessException {
        return MethodHandles.lookup().findVirtual(ExpressionArray.class, "groupBy",
                MethodType.methodType(Expression.class, Expression.class));
    }

    private static MethodHandle groupByKeyMethod() throws NoSuchMethodException, IllegalAccessException {
        return MethodHandles.lookup().findVirtual(ExpressionArray.class, "groupByKey",
                MethodType.methodType(Expression.class));
    }

    private static MethodHandle firstMethod() throws NoSuchMethodException, IllegalAccessException {
        return MethodHandles.lookup().findVirtual(ExpressionArray.class, "first",
                MethodType.methodType(Expression.class));
    }

    private static MethodHandle otherMethod() throws NoSuchMethodException, IllegalAccessException {
        return MethodHandles.lookup().findVirtual(ExpressionArray.class, "other",
                MethodType.methodType(Expression.class));
    }

    private static MethodHandle reverseMethod() throws NoSuchMethodException, IllegalAccessException {
        return MethodHandles.lookup().findVirtual(ExpressionArray.class, "reverse",
                MethodType.methodType(Expression.class));
    }

    private static MethodHandle countMethod() throws NoSuchMethodException, IllegalAccessException {
        return MethodHandles.lookup().findVirtual(ExpressionArray.class, "count",
                MethodType.methodType(Expression.class));
    }
}
