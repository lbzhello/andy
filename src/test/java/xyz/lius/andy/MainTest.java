package xyz.lius.andy;

import xyz.lius.andy.core.ReplEngine;
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
    public static void main(String[] args) {
        Main.main(new String[]{"docs/examples/test.andy"});
    }

    @Test
    public void andyTest() {
        operatorTest();
        functionTest();
        arrayTest();
        tmplTest();
        xmlTest();
    }

    @Test
    public void operatorTest() {
        ReplEngine replEngine = new ReplEngine();
        replEngine.evalFile("src/test/andy/operator.andy");
    }

    @Test
    public void functionTest() {
        ReplEngine replEngine = new ReplEngine();
        replEngine.evalFile("src/test/andy/function.andy");

    }

    @Test
    public void arrayTest() {
        ReplEngine replEngine = new ReplEngine();
        replEngine.evalFile("src/test/andy/array.andy");

    }

    @Test
    public void tmplTest() {
        ReplEngine replEngine = new ReplEngine();
        replEngine.evalFile("src/test/andy/tmpl.andy");

    }

    @Test
    public void xmlTest() {
        ReplEngine replEngine = new ReplEngine();
        replEngine.evalFile("docs/examples/xml.andy");

    }

}





