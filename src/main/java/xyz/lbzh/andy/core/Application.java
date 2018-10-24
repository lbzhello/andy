package xyz.lbzh.andy.core;

import xyz.lbzh.andy.expression.*;
import xyz.lbzh.andy.parser.Parser;

public class Application {
    public static final void start(Class<?> clazz, String[] args) {
        ApplicationContextBuilder.build(clazz);
        ReplEngine replEngine = new ReplEngine();
        Parser<Expression> parser = ApplicationFactory.get(Parser.class);
        replEngine.evalFile("andy.test");
    }
}
