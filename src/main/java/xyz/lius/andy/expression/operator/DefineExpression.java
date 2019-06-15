package xyz.lius.andy.expression.operator;

import xyz.lius.andy.expression.*;
import xyz.lius.andy.expression.ast.BracketExpression;
import xyz.lius.andy.expression.ast.CurlyBracketExpression;

@CurlyBracketed
public class DefineExpression extends AbstractContainer implements Operator {
    public DefineExpression(BracketExpression bracket, CurlyBracketExpression curlyBracket) {
        super(2);
        add(bracket);
        add(curlyBracket);
    }

    /**
     * Define will generate a ComplexExpression
     * @param context
     * @return
     */
    @Override
    public Expression eval(Context<Name, Expression> context) {
        BracketExpression bracket = (BracketExpression) get(0);
        CurlyBracketExpression curlyBracket = (CurlyBracketExpression) get(1);
        //every ComplexExpression has it's own context
        ComplexExpression complexExpression = curlyBracket.eval(context).setParameters(bracket.getParameters());
        context.add(bracket.getName(), complexExpression);
        return complexExpression;
    }

    @Override
    public String toString() {
        return show(ExpressionType.DEFINE.toString(), super.toString());
    }
}