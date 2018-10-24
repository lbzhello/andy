package xyz.lbzh.andy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.runtime.*;

/**
 * Supply runtime function
 */
@Configuration
public class RuntimeConfig {
    @Bean("=")
    @Scope("prototype")
    public Expression assign() {
        return new AssignExpression();
    }

    @Bean("+")
    @Scope("prototype")
    public Expression plus() {
        return new PlusExpression();
    }

    @Bean("-")
    @Scope("prototype")
    public Expression minus() {
        return new MinusExpression();
    }

    @Bean("*")
    @Scope("prototype")
    public Expression Multiply() {
        return new MultiplyExpression();
    }

    @Bean("/")
    @Scope("prototype")
    public Expression divide() {
        return new DivideExpression();
    }

    @Bean("==")
    @Scope("prototype")
    public Expression equal() {
        return new EqualExpression();
    }

    @Bean("!=")
    @Scope("prototype")
    public Expression notEqual() {
        return new NotEqualExpression();
    }

    @Bean(">")
    @Scope("prototype")
    public Expression greaterThan() {
        return new GtExpression();
    }

    @Bean(">=")
    @Scope("prototype")
    public Expression greaterEqual() {
        return new GeExpression();
    }

    @Bean("<")
    @Scope("prototype")
    public Expression lessThan() {
        return new LtExpression();
    }

    @Bean("<=")
    @Scope("prototype")
    public Expression lessEqual() {
        return new LeExpression();
    }

    @Bean("||")
    @Scope("prototype")
    public Expression or() {
        return new OrExpression();
    }

    @Bean("if")
    @Scope("prototype")
    public Expression ifExpression() {
        return new IfExpression();
    }

    @Bean("for")
    @Scope("prototype")
    public Expression forExpression() {
        return new ForExpression();
    }

    @Bean("->")
    @Scope("prototype")
    public Expression arrow() {
        return new ArrowExpression();
    }
}
