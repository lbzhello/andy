package fun.mandy;

import fun.mandy.expression.Expression;
import fun.mandy.expression.annotation.SExpressed;
import fun.mandy.expression.support.CommandExpression;
import fun.mandy.expression.support.SExpression;
import fun.mandy.expression.support.ValueExpression;
import org.junit.Test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.math.BigDecimal;

public class MainTest {
    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType methodType = MethodType.methodType(String.class,String.class);
        MethodHandle methodHandle = lookup.findVirtual(MainTest.class, "test",methodType);
        Object a = methodHandle.invoke(new MainTest(),"sss");
    }

    @Test
    public void NumberTest(){
        Object n = 223;
        BigDecimal a = new BigDecimal(n.toString());
        BigDecimal b = a.divide(new BigDecimal(3),5,BigDecimal.ROUND_HALF_DOWN);
        System.out.println(b);
    }

    @Test
    public void AnnotationTest() throws IllegalAccessException, InstantiationException {
        Expression expression = new CommandExpression(new ValueExpression("expr"));
        SExpressed sExpressed = expression.getClass().getDeclaredAnnotation(SExpressed.class);
        Class<? extends SExpression> v = sExpressed.value();
        SExpression o = v.newInstance();

        System.out.println();
    }

    @Test
    public void StringBufferTest(){
        StringBuffer sb = new StringBuffer("test ");
        sb.replace(4, 5, "");
        sb.append("0");
        System.out.println(sb);
    }

}


