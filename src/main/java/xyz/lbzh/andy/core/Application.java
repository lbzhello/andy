package xyz.lbzh.andy.core;

import xyz.lbzh.andy.expression.*;
import xyz.lbzh.andy.parser.Parser;

import java.io.IOException;

public class Application {
    public static final void start(Class<?> clazz, String[] args) throws IOException {
//        ApplicationContext applicationContext = new ApplicationContextBuilder().build(clazz);
//        Parser<Expression> parser = applicationContext.getBean(Parser.class);

        Parser<Expression> parser = new ObjectFactory().parser();

        Expression curlyBracket  = parser.parseFile("andy.test");
        System.out.println(curlyBracket);

        System.out.println();

        ReplEngine replEngine = new ReplEngine();
        replEngine.eval(curlyBracket);
    }
}
