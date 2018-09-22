package xyz.lbzh.andy.expression.support;

import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.annotation.SExpressed;

/**
 * command sign interface, help to parse expression
 */
@SExpressed
public class CommandExpression extends SExpression{
    public CommandExpression(Expression... expressions){
        super(expressions);
    }
}
