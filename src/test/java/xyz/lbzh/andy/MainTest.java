package xyz.lbzh.andy;

import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.annotation.RoundBracketed;
import xyz.lbzh.andy.expression.support.CommandExpression;
import xyz.lbzh.andy.expression.support.RoundBracketExpression;
import xyz.lbzh.andy.expression.support.ValueExpression;
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
        RoundBracketed roundBracketed = expression.getClass().getDeclaredAnnotation(RoundBracketed.class);
        Class<? extends RoundBracketExpression> v = roundBracketed.value();
        RoundBracketExpression o = v.newInstance();

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


