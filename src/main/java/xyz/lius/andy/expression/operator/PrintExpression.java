package xyz.lius.andy.expression.operator;

import xyz.lius.andy.core.Definition;
import xyz.lius.andy.core.OperatorSingleton;
import xyz.lius.andy.expression.*;
import xyz.lius.andy.expression.ast.BracketExpression;

public class PrintExpression extends AbstractContainer implements Operator {

    @Override
    public Expression eval(Context<Name, Expression> context) {
        BracketExpression rst = ExpressionFactory.bracket();
        for (Expression element : toArray()) {
            rst.add(element.eval(context));
        }
        System.out.println(rst);
        return Definition.NIL;
    }

    @Override
    public String toString() {
        return show(OperatorSingleton.PRINT, super.toString());
    }
}
