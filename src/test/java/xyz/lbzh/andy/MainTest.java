package xyz.lbzh.andy;

import xyz.lbzh.andy.core.Definition;
import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.ExpressionFactory;
import xyz.lbzh.andy.expression.RoundBracketed;
import xyz.lbzh.andy.expression.support.*;
import org.junit.Test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
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
        BigDecimal b = a.divide(new BigDecimal(3),5,RoundingMode.HALF_EVEN);
        System.out.println(b);
    }

    @Test
    public void AnnotationTest() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Expression expression = RoundBracketExpression.operator(new ValueExpression("expr"));
        RoundBracketed roundBracketed = expression.getClass().getDeclaredAnnotation(RoundBracketed.class);
        Class<? extends RoundBracketExpression> v = roundBracketed.value();
        RoundBracketExpression o = v.getDeclaredConstructor().newInstance();

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
        Expression b = ExpressionFactory.roundBracket(a);
        System.out.println(a.equals(b));
        System.out.println(b.equals(a));
        Map<Expression, Object> map = new HashMap<>();
        map.put(a, "t");
        map.put(b, "t2");
        System.out.println(map);
    }

    @Test
    public void objTest() {
        var list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        System.out.println(list.subList(1, 4));
    }

    @Test
    public void EnumTest() {
    }

    @Test
    public void DeTest() {
        Expression expression = new DelimiterExpression(Definition.SPACE + String.valueOf("("));
        System.out.println(expression);
    }

}


