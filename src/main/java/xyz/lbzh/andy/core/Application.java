package xyz.lbzh.andy.core;

import xyz.lbzh.andy.config.AppConfig;
import xyz.lbzh.andy.expression.Context;
import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.ExpressionContext;
import xyz.lbzh.andy.expression.Name;
import xyz.lbzh.andy.expression.runtime.ComplexExpression;
import xyz.lbzh.andy.parser.Parser;

import java.io.IOException;

public class Application {
    public static final void start(Class<?> clazz, String[] args) throws IOException {
//        ApplicationContext applicationContext = new ApplicationContextBuilder().build(clazz);
//        Parser<Expression> parser = applicationContext.getBean(Parser.class);

        Parser<Expression> parser = new ObjectFactory().parser();

        Expression curlyBracket  = parser.parse("lang.my");
        System.out.println(curlyBracket);

        System.out.println();

        Context<Name, Expression> rootContext = new ExpressionContext(Definition.getCoreContext());
        Expression complex = curlyBracket.eval(rootContext); //build and generate a runtime expression
        Expression rst = complex.eval(new ExpressionContext(rootContext));
        System.err.println(rst);
    }
}
