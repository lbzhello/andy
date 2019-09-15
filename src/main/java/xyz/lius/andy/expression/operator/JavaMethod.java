package xyz.lius.andy.expression.operator;

import xyz.lius.andy.core.Definition;
import xyz.lius.andy.expression.*;
import xyz.lius.andy.util.AbstractContainer;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * representative a java method
 * use reflect/invoke to invoke
 * @see ArrayMethod
 */
public class JavaMethod extends AbstractContainer implements Operator {
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
        Class<?>[] paramTypes = new Class[size()];
        Object[] paramValues = new Object[size()];
        for (int i = 0; i < size(); i++) {
            Expression param = get(i);
            if (TypeCheck.isString(param)) {
                paramTypes[i] = String.class;
                paramValues[i] = TypeCheck.asString(param).getValue();
            } else if (TypeCheck.isNumber(param)) {
                //number as int
                paramTypes[i] = int.class;
                paramValues[i] = TypeCheck.asNumber(param).intValue();
            } else if (TypeCheck.isJavaObject(param)) {
                paramTypes[i] = TypeCheck.asJavaObject(param).getObject().getClass();
                paramValues[i] = TypeCheck.asJavaObject(param).getObject();
            } else {  //type Expression
//                paramTypes[i] = param.getClass();
                paramTypes[i] = Expression.class; //通用类型参数
                paramValues[i] = param;
            }
        }

        try {
            Method method = methodClass.getMethod(methodName, paramTypes);
            MethodHandle methodHandle = MethodHandles.lookup().unreflect(method).asSpreader(Object[].class, size()).bindTo(methodObject);
            Object rstObj = methodHandle.invoke(paramValues);
            if (TypeCheck.isExpression(rstObj)) {
                return TypeCheck.asExpression(rstObj);
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
        return Definition.NIL;
    }

    @Override
    public String toString() {
        return Objects.isNull(methodObject) ? Objects.toString(methodClass) : methodObject.toString();
    }
}
