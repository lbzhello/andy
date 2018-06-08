package fun.mandy.boot;

import fun.mandy.expression.Expression;
import fun.mandy.parser.Parser;
import fun.mandy.tokenizer.Token;
import fun.mandy.tokenizer.Tokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;

public final class Application {
    private Application() {}
    private static AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

    @Component
    private static final class Builder {
        public void test(){
            System.out.println("i am Context inner Application");
        }

        public Builder register(Class<?> clazz){
            applicationContext.register(clazz);
            applicationContext.refresh();
            return this;
        }

    }




    public void run() {

    }

    public static final void start(Class<?> clazz, String[] args) throws IOException {
        applicationContext.register(clazz);
        applicationContext.refresh();

        Parser<Expression> parser = getApplicationContext().getBean(Parser.class);
        Reader reader = new FileReader(args[0]);
        parser.init(reader);
        parser.generate();
        parser.close();
        reader.close();
    }

    /**
     * Get Spring ApplicationContext
     * @return
     */
    public static final ApplicationContext getApplicationContext(){
        return  applicationContext;
    }



    /**
     * Deep clone
     * @param obj
     * @param <T>
     * @return
     */
    public static final <T> T copy(T obj){
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
