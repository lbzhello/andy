package fun.mandy;

import fun.mandy.core.ObjectFactory;
import fun.mandy.parser.Parser;
import org.junit.Test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.math.BigDecimal;

class MethodTest {
    public String test(String s){
        System.out.println(s);
        return s;
    }
}

public class ManinTest {
    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType methodType = MethodType.methodType(String.class,String.class);
        MethodHandle methodHandle = lookup.findVirtual(MethodTest.class, "test",methodType);
        Object a = methodHandle.invoke(new MethodTest(),"sss");
    }

    @Test
    public void NumberTest(){
        Object n = 223;
        BigDecimal a = new BigDecimal(n.toString());
        BigDecimal b = a.divide(new BigDecimal(3),5,BigDecimal.ROUND_HALF_DOWN);
        System.out.println(b);
    }

    @Test
    public void test() throws Throwable {
        Object o = ObjectFactory.create("parser");
        System.out.println(o);
    }

    void println(Object object) {
        System.out.println(object);
    }
}


