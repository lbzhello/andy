package xyz.lius.andy;

import xyz.lius.andy.expression.*;
import xyz.lius.andy.expression.ast.*;
import org.junit.Test;

import java.io.*;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Supplier;

public class MainTest {
    public static void main(String[] args) throws FileNotFoundException {
        File f = new File("docs/examples/tmp.andy");
        PrintWriter printWriter = new PrintWriter(f);
        printWriter.println("this is first line.");
        printWriter.println("this is first line.");
        printWriter.close();
    }

    @Test
    public void andyTest() {
        ReplEngine replEngine = new ReplEngine();
        replEngine.evalFile("src/main/andy/parser.andy");
    }

    @Test
    public void tmpTest() {
        ReplEngine replEngine = new ReplEngine();
        replEngine.evalFile("docs/examples/tmp.andy");
    }

    @Test
    public void mainTest() throws Exception {
        Main.main(new String[]{"docs/examples/test.andy"});
    }

    @Test
    public void tmplTest() {
        ReplEngine replEngine = new ReplEngine();
        replEngine.evalFile("docs/examples/tmpl.andy");

    }

    @Test
    public void xmlTest() {
        ReplEngine replEngine = new ReplEngine();
        replEngine.evalFile("docs/examples/xml.andy");

    }

    @Test
    public void iterTest() throws IOException {
        ReplEngine replEngine = new ReplEngine();
        replEngine.evalFile("docs/examples/iter.andy");
    }

    @Test
    public void parseTest() {
        ReplEngine replEngine = new ReplEngine();
        replEngine.eval("if 1 > 0 2");
    }

    @Test
    public void typeTest() {
        Object o = new Object();
        if (Supplier.class.isInstance(o)) {
            System.out.println("Supplier.class.isInstance(o)");
        }
        if (o instanceof Object) {

        }
    }

    @Test
    public void numberTest(){
        Object n = 223;
        BigDecimal a = new BigDecimal(n.toString());
        BigDecimal b = a.divide(new BigDecimal(3),5,RoundingMode.HALF_EVEN);
        System.out.println(b);
    }

    @Test
    public void annotationTest() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Expression expression = ExpressionFactory.curlyBracket();
        CurlyBracketed roundBracketed = expression.getClass().getDeclaredAnnotation(CurlyBracketed.class);
        Class<? extends CurlyBracketExpression> v = roundBracketed.value();

        System.out.println();
    }

    @Test
    public void stringBufferTest(){
        StringBuffer sb = new StringBuffer("test ");
        sb.replace(4, 5, "");
        sb.append("0");
        System.out.println(sb);
        System.out.println("" == null);
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
    public void paramTest() {
        Expression expr = ExpressionType.NIL;
        changeParam(expr);
        System.out.println(expr);
    }

    private void changeParam(Expression p1) {
        p1 = ExpressionFactory.symbol("hello");
        System.out.println(p1);
    }

    @Test
    public void methodTest() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType methodType = MethodType.methodType(String.class,String.class);
        MethodHandle methodHandle = lookup.findVirtual(MainTest.class, "printTest",methodType);
        Object a = methodHandle.invoke(new MainTest(),"sss");
    }

    @Test
    public void reflectTest() throws Throwable{
        Method method = MainTest.class.getMethod("printTest", String.class);
        MethodHandle mh = MethodHandles.lookup().unreflect(method);
        method.invoke(new MainTest(), "222");
//        MethodType methodType = MethodType.genericMethodType()
        MethodType methodType = MethodType.methodType(Expression.class, Expression.class);
        MethodHandle methodHandle = MethodHandles.lookup().findVirtual(SquareBracketExpression.class, "map", methodType);
        SquareBracketExpression list = new SquareBracketExpression(ExpressionFactory.symbol("1"));
        methodHandle = methodHandle.bindTo(list);
        Object o = methodHandle.invoke(new StringExpression("123"));
    }

    public String printTest(String str) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        System.out.println(str);
        str.getClass().getConstructor().newInstance();
        return str;
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





