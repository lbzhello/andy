package xyz.lbzh.andy.config;

import xyz.lbzh.andy.core.ApplicationFactory;
import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.parser.Parser;
import xyz.lbzh.andy.parser.support.DefaultParser;
import xyz.lbzh.andy.tokenizer.Token;
import xyz.lbzh.andy.tokenizer.Tokenizer;
import xyz.lbzh.andy.tokenizer.support.FileTokenizer;
import org.springframework.context.annotation.*;

@Configuration
@Import(RuntimeConfig.class)
@ComponentScan(basePackages = "xyz.lbzh.andy")
@PropertySource("classpath:application.properties")
public class AppConfig {
    @Bean
    public ApplicationFactory applicationFactory() {
        return new ApplicationFactory();
    }

    @Bean
    @Scope("prototype")
    public Tokenizer tokenizer(){
        Tokenizer<Token> tokenizer = new FileTokenizer();
        return tokenizer;
    }

    @Bean
    @Scope("prototype")
    public Parser parser(){
        Parser<Expression> parser = new DefaultParser(tokenizer());
        return parser;
    }

}
