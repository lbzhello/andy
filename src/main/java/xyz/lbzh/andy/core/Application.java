package xyz.lbzh.andy.core;

import xyz.lbzh.andy.config.ApplicationContextBuilder;
import xyz.lbzh.andy.expression.*;
import xyz.lbzh.andy.parser.Parser;

public class Application {
    public static final void start(Class<?> clazz, String[] args) {
        ApplicationContextBuilder.build(clazz);
        Parser<Expression> parser = ObjectFactory.getApplicationContext().getBean(Parser.class);

//        Parser<Expression> parser = new ObjectFactory().parser();

        Expression curlyBracket  = parser.parseFile("andy.test");
        System.out.println(curlyBracket);

        System.out.println();

        ReplEngine replEngine = new ReplEngine();
        replEngine.eval(curlyBracket);
    }
}
