package xyz.lbzh.andy.core;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import xyz.lbzh.andy.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import xyz.lbzh.andy.config.ApplicationContextBuilder;
import xyz.lbzh.andy.expression.Context;
import xyz.lbzh.andy.expression.ExpressionContext;

import javax.inject.Named;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

@Named
public class ApplicationFactory extends AppConfig implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        if (applicationContext == null) {
            //AppConfig.class as default
            ApplicationContextBuilder.build(AppConfig.class);
        }
        return applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

}
