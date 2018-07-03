package fun.mandy;

import fun.mandy.config.AppConfig;
import fun.mandy.core.Application;
import org.springframework.context.annotation.*;

/**
 * Hello world!
 *
 */
@Configuration
@Import(AppConfig.class)
public class Main {
    public static void main( String[] args ) throws Exception {
        System.out.println("Definition starting...");
        Application.start(Main.class,args);
        System.out.println("Definition ended");
    }

}

