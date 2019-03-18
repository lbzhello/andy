package xyz.lius.andy.core;

/**
 * 应用程序启动类
 */
public class Application {
    public static final void start(String[] args) {
        if (args.length != 1) {
            System.err.println("The application accepts only filename as a parameter!");
            return;
        }

        ReplEngine replEngine = new ReplEngine();
        replEngine.evalFile(args[0]);
    }
}
