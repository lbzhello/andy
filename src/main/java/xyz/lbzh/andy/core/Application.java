package xyz.lbzh.andy.core;

import xyz.lbzh.andy.expression.*;
import xyz.lbzh.andy.parser.Parser;

public class Application {
    public static final void start(Class<?> clazz, String[] args) {
        ApplicationContextBuilder.build(clazz); //init spring context

        if (args.length != 1) {
            System.err.println("The application accepts only filename as a parameter!");
            return;
        }

        ReplEngine replEngine = new ReplEngine();
        replEngine.evalFile(args[0]);
    }
}
