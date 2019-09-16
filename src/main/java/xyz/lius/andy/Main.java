package xyz.lius.andy;

import xyz.lius.andy.core.Application;

/**
 * Hello world!
 *
 */
public class Main {
    public static void main( String[] args ) throws Exception {
        System.out.println("*********************************");
        System.out.println("* *  Application starting...  * *");
        System.out.println("*********************************");

        Application.start(args);

        System.out.println("*********************************");
        System.out.println("* *  Application ended        * *");
        System.out.println("*********************************");
    }

}

