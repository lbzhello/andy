package fun.mandy;

import fun.mandy.tokenizer.Tokenizer;
import fun.mandy.util.StaticUtils;
import org.springframework.context.ApplicationContext;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) throws IOException {
        ApplicationContext ctx = StaticUtils.getApplicationContext();
        Tokenizer<String > tokenizer = ctx.getBean(Tokenizer.class);
        Reader reader = new FileReader("lang.my");
        tokenizer.init(reader);
        String s = tokenizer.nextToken();
        tokenizer.close();
        reader.close();
        System.out.println(args[0]);
    }
}
