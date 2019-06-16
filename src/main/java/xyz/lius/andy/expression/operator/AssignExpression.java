package xyz.lius.andy.expression.operator;

import xyz.lius.andy.core.Definition;
import xyz.lius.andy.core.OperatorSingleton;
import xyz.lius.andy.expression.*;
import xyz.lius.andy.expression.ast.CurlyBracketExpression;
import xyz.lius.andy.expression.ast.RoundBracketExpression;
import xyz.lius.andy.expression.context.ExpressionContext;
import xyz.lius.andy.util.AbstractContainer;

/**
 * e.g. left = "hello"
 */
public class AssignExpression extends AbstractContainer implements Operator {

    public AssignExpression() {
        super(2);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        Context<Name, Expression> bindContext = context;
        Name name;
        Expression value;
        if (get(0) instanceof RoundBracketExpression) { //lambda (f x) = ...
            RoundBracketExpression left = (RoundBracketExpression) this.get(0);
            if  (get(1) instanceof CurlyBracketExpression) { //define a function. e.g. f(x) = { x }
                CurlyBracketExpression right = (CurlyBracketExpression) get(1);
                name = left.getName();
                Complex complex = right.eval(context);
                complex.setParameters(left.getParameters());
                value = complex;
            } else { //e.g. f(x) = x + 1
                name = left.getName();
                Complex complex = ExpressionFactory.complex(new ExpressionContext(context));
                complex.setParameters(left.getParameters());
                complex.setCodes(new Expression[]{get(1)});
                value = complex;
            }
        } else if (get(0) instanceof PointExpression) { //e.g. (. a b) = ...
            PointExpression left = (PointExpression) get(0);
            Expression parent = left.get(0).eval(context);
            if (parent instanceof Complex) {
                bindContext = ((Complex) parent).getContext();
                name = left.get(1) instanceof RoundBracketExpression ? left.get(1).eval(context).getName() : left.get(1).getName();
                value = this.get(1).eval(context);
            } else {
                return ExpressionFactory.error(parent, "Left value should be ComplexExpression");
            }
        } else { //e.g. f = x + 1
            name = this.get(0).getName();
            value = this.get(1).eval(context);
        }
        bindContext.bind(name, value);
        return Definition.NIL;
    }

    @Override
    public String toString() {
        return show(OperatorSingleton.ASSIGN, super.toString());
    }
}
