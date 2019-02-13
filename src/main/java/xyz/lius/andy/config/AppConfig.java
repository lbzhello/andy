package xyz.lius.andy.config;

import xyz.lius.andy.core.ApplicationFactory;
import xyz.lius.andy.expression.Expression;
import xyz.lius.andy.parser.Parser;
import xyz.lius.andy.parser.support.DefaultParser;
import xyz.lius.andy.tokenizer.Token;
import xyz.lius.andy.tokenizer.Tokenizer;
import xyz.lius.andy.tokenizer.support.FileTokenizer;
import org.springframework.context.annotation.*;
import xyz.lius.andy.tokenizer.support.StringTokenizer;
import xyz.lius.andy.tokenizer.support.TemplateTokenizer;

@Configuration
@Import(RuntimeConfig.class)
@ComponentScan(basePackages = "xyz.lius.andy")
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

    @Bean
    @Scope("prototype")
    public Tokenizer<Token> templateTokenizer() {
        return new TemplateTokenizer();
    }

    @Bean
    @Scope("prototype")
    public Tokenizer<Token> stringTokenizer() {
        return new StringTokenizer();
    }

}
