package xyz.lius.andy.expression.base;

import xyz.lius.andy.expression.*;
import xyz.lius.andy.expression.ast.BracketExpression;
import xyz.lius.andy.expression.ast.CurlyBracketExpression;
import xyz.lius.andy.expression.ast.RoundBracketExpression;

/**
 * e.g. (...){...}
 */
@CurlyBracketed
public class LambdaExpression extends RoundBracketExpression {
    private BracketExpression bracket;
    private CurlyBracketExpression curlyBracket;

    public LambdaExpression(BracketExpression bracket, CurlyBracketExpression curlyBracket) {
        this.bracket = bracket;
        this.curlyBracket = curlyBracket;
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        //every ComplexExpression has it's own context
        return this.curlyBracket.eval(context).setParameters(bracket.getParameters());
    }
}