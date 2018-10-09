package xyz.lbzh.andy.core;

import xyz.lbzh.andy.config.AppConfig;
import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.ExpressionContext;
import xyz.lbzh.andy.parser.Parser;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class Application {
    public static final void start(Class<?> clazz, String[] args) throws IOException {
//        ApplicationContext applicationContext = new ApplicationContextBuilder().build(clazz);
//        Parser<Expression> parser = applicationContext.getBean(Parser.class);

        Parser<Expression> parser = new ObjectFactory().parser();

        Expression expression  = parser.parse("lang.my");
        System.out.println(expression);
    }
}
