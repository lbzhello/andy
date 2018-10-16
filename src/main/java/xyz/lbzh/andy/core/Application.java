package xyz.lbzh.andy.core;

import xyz.lbzh.andy.config.ApplicationContextBuilder;
import xyz.lbzh.andy.expression.*;

public class Application {
    public static final void start(Class<?> clazz, String[] args) {
        ApplicationContextBuilder.build(clazz);
        ReplEngine replEngine = new ReplEngine();
        replEngine.evalFile("andy.test");
        replEngine.eval("3 + 2");
        replEngine.eval("8 + 9");
    }
}
