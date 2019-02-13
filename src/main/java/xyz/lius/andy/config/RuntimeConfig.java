package xyz.lius.andy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import xyz.lius.andy.expression.Expression;
import xyz.lius.andy.expression.ExpressionType;
import xyz.lius.andy.expression.runtime.*;

/**
 * Supply runtime function
 */
@Configuration
public class RuntimeConfig {
    @Bean("=")
    @Scope("prototype")
    public AssignExpression assign() {
        return new AssignExpression();
    }

    @Bean("+")
    @Scope("prototype")
    public PlusExpression plus() {
        return new PlusExpression();
    }

    @Bean("-")
    @Scope("prototype")
    public MinusExpression minus() {
        return new MinusExpression();
    }

    @Bean("*")
    @Scope("prototype")
    public MultiplyExpression Multiply() {
        return new MultiplyExpression();
    }

    @Bean("/")
    @Scope("prototype")
    public DivideExpression divide() {
        return new DivideExpression();
    }

    @Bean("==")
    @Scope("prototype")
    public EqualExpression equal() {
        return new EqualExpression();
    }

    @Bean("!=")
    @Scope("prototype")
    public NotEqualExpression notEqual() {
        return new NotEqualExpression();
    }

    @Bean(">")
    @Scope("prototype")
    public GtExpression greaterThan() {
        return new GtExpression();
    }

    @Bean(">=")
    @Scope("prototype")
    public GeExpression greaterEqual() {
        return new GeExpression();
    }

    @Bean("<")
    @Scope("prototype")
    public LtExpression lessThan() {
        return new LtExpression();
    }

    @Bean("<=")
    @Scope("prototype")
    public LeExpression lessEqual() {
        return new LeExpression();
    }

    @Bean("||")
    @Scope("prototype")
    public OrExpression or() {
        return new OrExpression();
    }

    @Bean("true")
    @Scope("prototype")
    public Expression trueExpression() {
        return ExpressionType.TRUE;
    }

    @Bean("false")
    @Scope("prototype")
    public Expression falseExpression() {
        return ExpressionType.FALSE;
    }

    @Bean("nil")
    @Scope("prototype")
    public Expression nil() {
        return ExpressionType.NIL;
    }

    @Bean("return")
    @Scope("prototype")
    public ReturnBuilderExpression returnExpression() {
        return new ReturnBuilderExpression();
    }

    @Bean("if")
    @Scope("prototype")
    public IfExpression ifExpression() {
        return new IfExpression();
    }

    @Bean("for")
    @Scope("prototype")
    public ForExpression forExpression() {
        return new ForExpression();
    }

    @Bean("->")
    @Scope("prototype")
    public ArrowExpression arrow() {
        return new ArrowExpression();
    }

    @Bean("print")
    @Scope("prototype")
    public PrintExpression print() {
        return new PrintExpression();
    }

    @Bean("new")
    @Scope("prototype")
    public NewExpression newExpression() {
        return new NewExpression();
    }

}
