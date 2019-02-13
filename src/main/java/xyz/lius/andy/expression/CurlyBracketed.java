package xyz.lius.andy.expression;

import xyz.lius.andy.expression.ast.CurlyBracketExpression;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurlyBracketed {
    Class<? extends CurlyBracketExpression>  value() default CurlyBracketExpression.class;
}
