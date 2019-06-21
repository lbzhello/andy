package xyz.lius.andy.expression.operator;

import xyz.lius.andy.expression.*;
import xyz.lius.andy.expression.ast.BracketExpression;
import xyz.lius.andy.expression.ast.CurlyBracketExpression;
import xyz.lius.andy.util.AbstractContainer;

/**
 * e.g. (...){...}
 */
public class LambdaExpression extends AbstractContainer implements Operator {
    public LambdaExpression() {
        super(2);
    }

    @Override
    public Complex eval(Context<Name, Expression> context) {
        BracketExpression bracket = (BracketExpression) get(0);
        CurlyBracketExpression curlyBracket = (CurlyBracketExpression) get(1);
        //every ComplexExpression has it's own context
        Complex complex = curlyBracket.eval(context);
        complex.setParameters(bracket.getParameters());
        return complex;
    }
}