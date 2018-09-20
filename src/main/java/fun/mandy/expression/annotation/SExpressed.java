package fun.mandy.expression.annotation;

import fun.mandy.expression.support.SExpression;

import java.lang.annotation.*;

/**
 * 此注解表示该表达式已经被解析为 S-Expression
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SExpressed {
    Class<? extends SExpression> value() default SExpression.class;
}
