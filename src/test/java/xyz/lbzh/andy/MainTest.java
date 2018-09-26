package xyz.lbzh.andy;

import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.ExpressionBuilder;
import xyz.lbzh.andy.expression.Name;
import xyz.lbzh.andy.expression.RoundBracketed;
import xyz.lbzh.andy.expression.support.*;
import org.junit.Test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

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
        Expression expression = RoundBracketExpression.operator(new ValueExpression("expr"));
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

    @Test
    public void roundBracketTest() {
        RoundBracketExpression r = RoundBracketExpression.operator(new SymbolExpression("22"));
    }

    @Test
    public void equalTest() {
        Expression a = new ValueExpression("t");
        Expression b = ExpressionBuilder.roundBracket(a);
        System.out.println(a.equals(b));
        System.out.println(b.equals(a));
        Map<Expression, Object> map = new HashMap<>();
        map.put(a, "t");
        map.put(b, "t2");
        System.out.println(map);
    }

    @Test
    public void objTest() {
        Expression a = ExpressionBuilder.roundBracket(ExpressionBuilder.roundBracket(new ValueExpression("a")));
        System.out.println(a.toName());
    }

}


