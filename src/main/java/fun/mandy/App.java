package fun.mandy;

import com.sun.org.apache.xerces.internal.xs.StringList;
import fun.mandy.config.AppConfig;
import fun.mandy.tokenizer.Tokenizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) throws FileNotFoundException {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        Tokenizer<String > tokenizer = ctx.getBean(Tokenizer.class);
        Reader reader = new FileReader("lang.my");
        tokenizer.init(reader);
        String s = tokenizer.nextToken();
        System.out.println(args[0]);
    }
}
