package xyz.lbzh.andy.expression;

import xyz.lbzh.andy.expression.support.CurlyBracketExpression;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurlyBracketed {
    Class<? extends CurlyBracketExpression>  value() default CurlyBracketExpression.class;
}
