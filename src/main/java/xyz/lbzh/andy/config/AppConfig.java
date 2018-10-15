package xyz.lbzh.andy.config;

import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.parser.Parser;
import xyz.lbzh.andy.parser.support.DefaultParser;
import xyz.lbzh.andy.tokenizer.LineNumberToken;
import xyz.lbzh.andy.tokenizer.Token;
import xyz.lbzh.andy.tokenizer.Tokenizer;
import xyz.lbzh.andy.tokenizer.support.DefaultTokenizer;
import org.springframework.context.annotation.*;

@Configuration
//@ComponentScan(basePackages = "xyz.lbzh.andy")
//@ImportResource("classpath:/fun/mandy/config/app-config.xml")
public class AppConfig {
    @Bean
    public  Tokenizer tokenizer(){
        Tokenizer<Token> tokenizer = new DefaultTokenizer();
        return tokenizer;
    }

    @Bean
    public  Parser parser(){
        Parser<Expression> parser = new DefaultParser(tokenizer());
        return parser;
    }

}
