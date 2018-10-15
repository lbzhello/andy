package xyz.lbzh.andy.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextBuilder {
    public static ApplicationContext build(Class<?> clazz){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(clazz);
        applicationContext.refresh();
        return applicationContext;
    }

}
