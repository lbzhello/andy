package fun.mandy.config;

import fun.mandy.tokenizer.Token;
import fun.mandy.tokenizer.Tokenizer;
import fun.mandy.tokenizer.support.DefaultToken;
import fun.mandy.tokenizer.support.DefaultTokenizer;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(basePackages = "fun.mandy")
@ImportResource("classpath:/fun/mandy/config/app-config.xml")
public class AppConfig {
    @Bean
    public Tokenizer tokenizer(){
        Tokenizer<Token<Integer,String>> tokenizer = new DefaultTokenizer();
        return tokenizer;
    }

    @Bean
    @Scope("prototype")
    public Token<Integer, String> token(){
        return new DefaultToken(0,"");
    }


}
