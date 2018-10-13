package xyz.lbzh.andy;

import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.ExpressionFactory;
import xyz.lbzh.andy.expression.RoundBracketed;
import xyz.lbzh.andy.expression.support.*;
import org.junit.Test;

import java.io.*;
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
        Expression expression = ExpressionFactory.roundBracket(ExpressionFactory.token("expr"));
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
        BracketExpression r = ExpressionFactory.roundBracket(ExpressionFactory.symbol("22"));
    }

    @Test
    public void equalTest() {
        Expression a = ExpressionFactory.token("t");
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
        EqTest o = new EqTest();
        System.out.println(o.equals(o));
    }

    /**
     * Deep clone
     * @param obj
     * @param <T>
     * @return
     */
    public static final <T> T copy(T obj){
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);

            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            return (T)ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }

}

class EqTest {
    @Override
    public boolean equals(Object obj) {
        return false;
    }
}




