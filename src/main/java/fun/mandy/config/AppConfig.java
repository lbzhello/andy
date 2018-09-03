package fun.mandy.config;

import fun.mandy.expression.Expression;
import fun.mandy.expression.support.ValueExpression;
import fun.mandy.parser.Parser;
import fun.mandy.parser.support.DefaultParser;
import fun.mandy.tokenizer.Tokenizer;
import fun.mandy.tokenizer.support.DefaultTokenizer;
import org.springframework.context.annotation.*;

@Configuration
//@ComponentScan(basePackages = "fun.mandy")
//@ImportResource("classpath:/fun/mandy/config/app-config.xml")
public class AppConfig {
    @Bean
    public  Tokenizer tokenizer(){
        Tokenizer<Expression> tokenizer = new DefaultTokenizer();
        return tokenizer;
    }

    @Bean
    public  Parser parser(){
        Parser<Expression> parser = new DefaultParser(tokenizer());
        return parser;
    }

}
