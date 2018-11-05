package xyz.lbzh.andy.expression.runtime;

import org.springframework.util.StringUtils;
import xyz.lbzh.andy.expression.*;
import xyz.lbzh.andy.expression.ast.SquareBracketExpression;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MethodExpression extends NativeExpression {
    private String methodName = "";
    private Class<?> methodClass;
    private Object methodObject;

    public MethodExpression(Object methodObject) {
        this.methodObject = methodObject;
        this.methodClass = methodObject.getClass();
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        if (Objects.isNull(methodName)) return this;
        List<Class<?>> paramTypes = new ArrayList<>(this.list().size());
        List<Object> paramValues = new ArrayList<>(this.list().size());
        for (Expression expression : list()) {
            Expression param = expression.eval(context);
            if (ExpressionUtils.isString(param)) {
                paramTypes.add(String.class);
                paramValues.add(ExpressionUtils.asString(param).getValue());
            } else if (ExpressionUtils.isNumber(param)) {
                //number as int
                paramTypes.add(int.class);
                paramValues.add(ExpressionUtils.asNumber(param).intValue());
            } else if (ExpressionUtils.isMethod(param)) {
                paramTypes.add(ExpressionUtils.asMethod(param).getMethodClass());
                paramValues.add(ExpressionUtils.asMethod(param).getMethodObject());
            } else {  //type Expression
//                paramTypes.add(param.getClass());
                paramTypes.add(Expression.class); //通用类型参数
                paramValues.add(param);
            }
        }

        try {
            Method method = methodClass.getMethod(methodName, paramTypes.toArray(new Class[paramTypes.size()]));
            MethodHandle methodHandle = MethodHandles.lookup().unreflect(method).asSpreader(Object[].class, paramValues.size()).bindTo(methodObject);
            Object rstObj = methodHandle.invoke(paramValues.toArray());
            if (ExpressionUtils.isExpression(rstObj)) {
                return (Expression)rstObj;
            } else {
                return new MethodExpression(rstObj);
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

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?> getMethodClass() {
        return methodClass;
    }

    public void setMethodClass(Class<?> methodClass) {
        this.methodClass = methodClass;
    }

    public Object getMethodObject() {
        return methodObject;
    }

    public void setMethodObject(Object methodObject) {
        this.methodObject = methodObject;
    }

    @Override
    public String toString() {
        return Objects.isNull(methodObject) ? Objects.toString(methodClass) : methodObject.toString();
    }
}
