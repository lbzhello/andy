package xyz.lbzh.andy;

import xyz.lbzh.andy.core.ApplicationFactory;
import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.ExpressionFactory;
import xyz.lbzh.andy.expression.ReplEngine;
import xyz.lbzh.andy.expression.RoundBracketed;
import xyz.lbzh.andy.expression.ast.BracketExpression;
import xyz.lbzh.andy.expression.ast.RoundBracketExpression;
import org.junit.Test;
import xyz.lbzh.andy.parser.Parser;

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
    public void replTest() {
        ReplEngine replEngine = new ReplEngine();

        Expression assign = ExpressionFactory.symbol("=");
        Expression plus = ExpressionFactory.symbol("+");
        Expression p1 = ExpressionFactory.number("344");
        Expression p2 = ExpressionFactory.number(123);
        Expression a = ExpressionFactory.symbol("a");

        Expression rst1 = ExpressionFactory.roundBracket(plus, p1, p2);
        Expression rst2 = ExpressionFactory.roundBracket(assign, a, rst1);
        replEngine.eval(rst2);
        replEngine.eval(a);
    }

    @Test
    public void iterTest() throws IOException {
        LineNumberReader reader = new LineNumberReader(new StringReader("123"));
        while (true) {
            System.out.println(reader.read());
        }
    }

    @Test
    public void parseTest() {
        ReplEngine replEngine = new ReplEngine();
        Parser<Expression> parser = ApplicationFactory.getBean(Parser.class);
        System.out.println(parser.parseFile("andy.test"));
        replEngine.eval(parser.parseFile("andy.test"));
        replEngine.eval("a = 5");
        replEngine.eval("a / 8");
        replEngine.eval("test3.key");
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




