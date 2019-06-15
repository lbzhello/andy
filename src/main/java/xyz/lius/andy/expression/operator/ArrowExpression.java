package xyz.lius.andy.expression.operator;

import xyz.lius.andy.core.OperatorSingleton;
import xyz.lius.andy.expression.*;
import xyz.lius.andy.expression.ast.BracketExpression;
import xyz.lius.andy.expression.ast.CurlyBracketExpression;
import xyz.lius.andy.expression.ast.RoundBracketExpression;

/**
 * e.g. left -> right
 */
public class ArrowExpression extends AbstractContainer implements Operator {
    public ArrowExpression() {
        super(2);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        BracketExpression left;
        CurlyBracketExpression right;
        if (ExpressionUtils.isSquareBracket(get(0)) || ExpressionUtils.isComma(get(0))) { //e.g. (x, y, z) -> right
            left = (BracketExpression)get(0);
        } else if (ExpressionUtils.isRoundBracket(get(0))) {
            BracketExpression roundBracket = (RoundBracketExpression) get(0);
            if (roundBracket.size() == 1) { //e.g. (x) -> right
                left = ExpressionFactory.squareBracket(roundBracket.get(0));
            } else { //e.g. (x y z) -> right
                return ExpressionFactory.error(roundBracket, "Error ArrowExpression!");
            }
        } else { //e.g. x -> right
            left = ExpressionFactory.squareBracket(get(0));
        }

        if (ExpressionUtils.isCurlyBracket(get(1))) { //e.g. left -> {...}
            right = (CurlyBracketExpression) get(1);
        } else { //e.g. left -> x + 1
            right = ExpressionFactory.curlyBracket();
            right.add(get(1));
        }
        return ExpressionFactory.lambda(left, right).eval(context);
    }

    @Override
    public String toString() {
        return show(OperatorSingleton.ARROW, super.toString());
    }
}
