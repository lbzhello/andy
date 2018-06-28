package fun.mandy.boot;

import fun.mandy.expression.Expression;
import fun.mandy.parser.Parser;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.*;
import java.security.PublicKey;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class Application {
    private Application() {}

    private static AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
    private static Map<String,Integer> operator = new HashMap<>();

    static {
        operator.put("=", 0);

        operator.put("||", 11);
        operator.put("&&", 12);

        operator.put("==", 21);
        operator.put(">=", 21);
        operator.put("<=", 21);

        operator.put("+", 31);
        operator.put("-",31);

        operator.put("*", 41);
        operator.put("/", 41);

        operator.put("++", 51);
        operator.put("--", 51);

        operator.put("!", 1113);
        operator.put(".", 1314);
    }

    @Named
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

        Reader reader = new FileReader(args[0]);

        Parser<Expression> parser = getApplicationContext().getBean(Parser.class);
        parser.init(reader);
        Expression expression = parser.next();
        //System.out.println(expression.getClass());
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

    public static boolean isOperator(String op) {
        return operator.keySet().contains(op);
    }

    public static int getPriority(String op) {
        return operator.getOrDefault(op,1);
    }

    /**
     *
     * @param op1
     * @param op2
     * @return
     *      0 -> getPriority(op1) = getPriority(op2)
     *      1 -> getPriority(op1) > getPriority(op2)
     *      -1 -> getPriority(op1) < getPriority(op2)
     */
    public static int getPriority(String op1, String op2) {
        return getPriority(op1) == getPriority(op2) ? 0 : getPriority(op1) > getPriority(op2) ? 1 : -1;
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
