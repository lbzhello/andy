package fun.mandy;

import fun.mandy.config.AppConfig;
import fun.mandy.util.Application;
import org.springframework.context.annotation.*;

/**
 * Hello world!
 *
 */
@Configuration
@Import(AppConfig.class)
public class Main {
    public static void main( String[] args ) throws Exception {
        System.out.println("Application starting...");
        Application.run(Main.class,args);
        System.out.println("Application ended");
    }

}

