package xyz.lius.andy.expression;

import xyz.lius.andy.expression.ast.RoundBracketExpression;

import java.lang.annotation.*;

/**
 * 此注解表示该表达式已经被解析为 RoundBracketExpression
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RoundBracketed {
    Class<? extends RoundBracketExpression> value() default RoundBracketExpression.class;
}
