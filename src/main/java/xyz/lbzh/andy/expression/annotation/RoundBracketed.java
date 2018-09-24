package xyz.lbzh.andy.expression.annotation;

import xyz.lbzh.andy.expression.support.RoundBracketExpression;

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