package xyz.lbzh.andy.expression.runtime;

import xyz.lbzh.andy.expression.*;
import xyz.lbzh.andy.expression.ast.BracketExpression;
import xyz.lbzh.andy.expression.ast.CurlyBracketExpression;
import xyz.lbzh.andy.expression.ast.RoundBracketExpression;

@CurlyBracketed
public class DefineExpression extends RoundBracketExpression {
    private BracketExpression bracket;
    private CurlyBracketExpression curlyBracket;

    public DefineExpression(BracketExpression bracket, CurlyBracketExpression curlyBracket) {
        super(ExpressionType.DEFINE, bracket, curlyBracket);
        this.bracket = bracket;
        this.curlyBracket = curlyBracket;
    }

    /**
     * Define will generate a ComplexExpression
     * @param context
     * @return
     */
    @Override
    public Expression eval(Context<Name, Expression> context) {
        //every ComplexExpression has it's own context
        ComplexExpression complexExpression = this.curlyBracket.eval(context).parameters(bracket.getParameters());
        context.newbind(bracket.getName(), complexExpression);
        return complexExpression;
    }
}