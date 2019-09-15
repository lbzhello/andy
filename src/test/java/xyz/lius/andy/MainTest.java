package xyz.lius.andy;

import xyz.lius.andy.interpreter.ReplEngine;
import org.junit.Test;

public class MainTest {
    public static void main(String[] args) {
        Main.main(new String[]{"src/test/andy/test.andy"});
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





