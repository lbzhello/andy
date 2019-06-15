package xyz.lius.andy.expression.operator;

import xyz.lius.andy.expression.*;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * use reflect/invoke to invoke a java method
 * @see ArrayMethod
 */
public class JavaMethod extends AbstractContainer implements Expression {
    private String methodName = "";
    private Class<?> methodClass;
    private Object methodObject;

    public JavaMethod(Object methodObject, String methodName) {
        this.methodName = methodName;
        this.methodObject = methodObject;
        this.methodClass = methodObject.getClass();
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        Class<?>[] paramTypes = new Class[count()];
        Object[] paramValues = new Object[count()];
        for (int i = 0; i < count(); i++) {
            Expression param = get(i);
            if (ExpressionUtils.isString(param)) {
                paramTypes[i] = String.class;
                paramValues[i] = ExpressionUtils.asString(param).getValue();
            } else if (ExpressionUtils.isNumber(param)) {
                //number as int
                paramTypes[i] = int.class;
                paramValues[i] = ExpressionUtils.asNumber(param).intValue();
            } else if (ExpressionUtils.isJavaObject(param)) {
                paramTypes[i] = ExpressionUtils.asJavaObject(param).getObject().getClass();
                paramValues[i] = ExpressionUtils.asJavaObject(param).getObject();
            } else {  //type Expression
//                paramTypes[i] = param.getClass();
                paramTypes[i] = Expression.class; //通用类型参数
                paramValues[i] = param;
            }
        }

        try {
            Method method = methodClass.getMethod(methodName, paramTypes);
            MethodHandle methodHandle = MethodHandles.lookup().unreflect(method).asSpreader(Object[].class, count()).bindTo(methodObject);
            Object rstObj = methodHandle.invoke(paramValues);
            if (ExpressionUtils.isExpression(rstObj)) {
                return ExpressionUtils.asExpression(rstObj);
            } else {
                return ExpressionFactory.javaObject(rstObj);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return ExpressionType.NIL;
    }

    @Override
    public String toString() {
        return Objects.isNull(methodObject) ? Objects.toString(methodClass) : methodObject.toString();
    }
}
