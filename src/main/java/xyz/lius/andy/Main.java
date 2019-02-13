package xyz.lius.andy;

import xyz.lius.andy.config.AppConfig;
import xyz.lius.andy.core.Application;
import org.springframework.context.annotation.*;

/**
 * Hello world!
 *
 */
@Configuration
@Import(AppConfig.class)
public class Main {
    public static void main( String[] args ) throws Exception {
        System.out.println("*********************************");
        System.out.println("* *  Application starting...  * *");
        System.out.println("*********************************");

        Application.start(Main.class,args);

        System.out.println("*********************************");
        System.out.println("* *  Application ended        * *");
        System.out.println("*********************************");
    }

}

