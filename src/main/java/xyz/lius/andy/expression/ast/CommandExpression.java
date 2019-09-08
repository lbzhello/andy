package xyz.lius.andy.expression.ast;

import xyz.lius.andy.core.Definition;
import xyz.lius.andy.expression.Context;
import xyz.lius.andy.expression.Expression;
import xyz.lius.andy.expression.Name;

/**
 * 可以不带括号的括号的表达式
 * e.g. max a b
 */
public class CommandExpression extends RoundBracketExpression {

    public CommandExpression(Expression head) {
        super(head);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        return Definition.NIL;
    }
}
