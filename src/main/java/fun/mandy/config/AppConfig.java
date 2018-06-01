package fun.mandy.config;

import fun.mandy.tokenizer.Tokenizer;
import fun.mandy.tokenizer.support.DefaultTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan(basePackages = "fun.mandy")
@ImportResource("classpath:/fun/mandy/config/app-config.xml")
public class AppConfig {
    @Bean
    public Tokenizer tokenizer(){
        return new DefaultTokenizer();
    }
}
