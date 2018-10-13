package xyz.lbzh.andy.expression.runtime;

import xyz.lbzh.andy.expression.*;
import xyz.lbzh.andy.expression.ast.BracketExpression;
import xyz.lbzh.andy.expression.ast.CurlyBracketExpression;
import xyz.lbzh.andy.expression.ast.RoundBracketExpression;

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
        return this.curlyBracket.eval(new ExpressionContext(context)).parameters(bracket.getParameters());
    }
}