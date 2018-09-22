package xyz.lbzh.andy.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextBuilder {
    private static ApplicationContext rootApplicationContext = null;

    public ApplicationContext build(Class<?> clazz){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(clazz);
        applicationContext.refresh();
        return applicationContext;
    }

    public static ApplicationContext getRootApplicationContext() {
        return rootApplicationContext;
    }

    public static void setRootApplicationContext(ApplicationContext rootApplicationContext) {
        ApplicationContextBuilder.rootApplicationContext = rootApplicationContext;
    }
}
