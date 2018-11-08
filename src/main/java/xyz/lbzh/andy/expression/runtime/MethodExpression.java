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

    public MethodExpression(Object methodObject, String methodName) {
        this.methodName = methodName;
        this.methodObject = methodObject;
        this.methodClass = methodObject.getClass();
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
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
            } else if (ExpressionUtils.isObject(param)) {
                paramTypes.add(ExpressionUtils.asObject(param).getObject().getClass());
                paramValues.add(ExpressionUtils.asObject(param).getObject());
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
                return ExpressionUtils.asExpression(rstObj);
            } else {
                return ExpressionFactory.object(rstObj);
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
