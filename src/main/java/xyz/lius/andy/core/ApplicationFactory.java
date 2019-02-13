package xyz.lius.andy.core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import xyz.lius.andy.config.AppConfig;

public class ApplicationFactory implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        if (applicationContext == null) {
            //AppConfig.class as default
            ApplicationContextBuilder.build(AppConfig.class);
        }
        return applicationContext;
    }

    public static <T> T get(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    public static <T> T get(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }
}
