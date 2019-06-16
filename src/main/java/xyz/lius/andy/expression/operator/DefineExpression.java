package xyz.lius.andy.expression.operator;

import xyz.lius.andy.core.OperatorSingleton;
import xyz.lius.andy.expression.*;
import xyz.lius.andy.expression.ast.BracketExpression;
import xyz.lius.andy.expression.ast.CurlyBracketExpression;
import xyz.lius.andy.util.AbstractContainer;

@CurlyBracketed
public class DefineExpression extends AbstractContainer implements Operator {
    public DefineExpression() {
        super(2);
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
        Complex complex = curlyBracket.eval(context);
        complex.setParameters(bracket.getParameters());
        context.add(bracket.getName(), complex);
        return complex;
    }

    @Override
    public String toString() {
        return show(OperatorSingleton.DEFINE, super.toString());
    }
}