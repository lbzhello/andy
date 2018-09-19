package fun.mandy.expression.support;

import fun.mandy.expression.Expression;

/**
 * command sign interface, help to parse expression
 */
public class CommandExpression extends SExpression{
    public CommandExpression(Expression... expressions){
        super(expressions);
    }
}
