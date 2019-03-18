package xyz.lius.andy;

import xyz.lius.andy.core.Application;

/**
 * Hello world!
 *
 */
public class Main {
    public static void main( String[] args ) {
        System.out.println("*********************************");
        System.out.println("* *  Application starting...  * *");
        System.out.println("*********************************");

        Application.start(Main.class,args);

        System.out.println("*********************************");
        System.out.println("* *  Application ended        * *");
        System.out.println("*********************************");
    }

}

