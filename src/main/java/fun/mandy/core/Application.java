package fun.mandy.core;

import fun.mandy.config.AppConfig;
import fun.mandy.expression.Expression;
import fun.mandy.parser.Parser;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class Application {
    public static final void start(Class<?> clazz, String[] args) throws IOException {
//        ApplicationContext applicationContext = new ApplicationContextBuilder().build(clazz);
//        Parser<Expression> parser = applicationContext.getBean(Parser.class);

        Reader reader = new FileReader(args[0]);

        Parser<Expression> parser = new ObjectFactory().parser();
        parser.init(reader);

        Expression expression = Definition.HOF;
        while (expression != Definition.EOF) {
            expression = parser.next();
            System.out.println(expression);
        }

        parser.close();
        reader.close();
    }
}
