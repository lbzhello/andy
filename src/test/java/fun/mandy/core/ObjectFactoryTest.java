package fun.mandy.core;

import fun.mandy.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class ObjectFactoryTest extends AppConfig {
    public static AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

    private static AppConfig appConfig = new AppConfig();

    public static <T> T create(Class<T> clazz) throws Throwable {
        MethodType methodType = MethodType.methodType(clazz);
        String methodName = clazz.getSimpleName();
        methodName = Character.toLowerCase(methodName.charAt(0)) + methodName.substring(1);
        MethodHandle methodHandle = MethodHandles.lookup().findVirtual(AppConfig.class,methodName,methodType);
        return (T)methodHandle.invoke(appConfig);
    }

    public static <T> T create(String name) throws ClassNotFoundException {
        return (T) getApplicationContext().getBean(name);
    }

    public static AnnotationConfigApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(AnnotationConfigApplicationContext applicationContext) {
        applicationContext = applicationContext;
    }
}
