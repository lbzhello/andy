package xyz.lius.andy.expression.operator;

import xyz.lius.andy.core.Definition;
import xyz.lius.andy.core.OperatorSingleton;
import xyz.lius.andy.expression.*;
import xyz.lius.andy.expression.ast.CurlyBracketExpression;
import xyz.lius.andy.expression.ast.RoundBracketExpression;
import xyz.lius.andy.expression.context.ExpressionContext;
import xyz.lius.andy.expression.strategy.LetUtils;
import xyz.lius.andy.util.AbstractContainer;
import xyz.lius.andy.util.Pair;

/**
 * e.g. left = "hello"
 */
public class AssignExpression extends AbstractContainer implements Operator {

    public AssignExpression() {
        super(2);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        return LetUtils.eval(context, new Pair<>(get(0), get(1)), false);
    }

    @Override
    public String toString() {
        return show(OperatorSingleton.ASSIGN, super.toString());
    }
}
