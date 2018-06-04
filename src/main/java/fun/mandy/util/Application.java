package fun.mandy.util;

import fun.mandy.tokenizer.Tokenizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public final class Application {
    private Application() {}
    private static AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
    private static Set<Character> delimiters = new HashSet<>();

    static {
        delimiters.add('.');
        delimiters.add(':');
        delimiters.add('(');
        delimiters.add(')');
        delimiters.add('{');
        delimiters.add('}');
        delimiters.add('"');
        delimiters.add('/');
        delimiters.add('\'');
        delimiters.add('\\');
    }

    public static void run(Class<?> clazz, String[] args) {
        applicationContext.register(clazz);
        applicationContext.refresh();

        try {
            Tokenizer<String > tokenizer = getApplicationContext().getBean(Tokenizer.class);
            Reader reader = new FileReader(args[0]);
            tokenizer.init(reader);
            String s = tokenizer.nextToken();
            tokenizer.close();
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get Spring ApplicationContext
     * @return
     */
    public static final ApplicationContext getApplicationContext(){
        return  applicationContext;
    }

    public static final boolean isDelimiter(Character c){
        return delimiters.contains(c);
    }

    public static final void addDelimiter(Character c){
        delimiters.add(c);
    }

    public static final void removeDelimiter(Character c){
        delimiters.remove(c);
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
