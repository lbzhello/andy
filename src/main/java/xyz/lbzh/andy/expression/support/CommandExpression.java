package xyz.lbzh.andy.expression.support;

import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.annotation.RoundBracketed;

/**
 * command sign interface, help to parse expression
 */
@RoundBracketed
public class CommandExpression extends RoundBracketExpression {
    public CommandExpression(Expression... expressions){
        super(expressions);
    }
}
