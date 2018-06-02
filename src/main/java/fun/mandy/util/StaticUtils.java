package fun.mandy.util;

import fun.mandy.config.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.*;

public final class StaticUtils {
    private static ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

    /**
     * Get Spring ApplicationContext
     * @return
     */
    public static ApplicationContext getApplicationContext(){
        return  applicationContext;
    }

    /**
     * Deep clone
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> T copy(T obj){
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);

            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            return (T)ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }
}
