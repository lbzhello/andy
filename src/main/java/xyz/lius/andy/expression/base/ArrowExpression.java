package xyz.lius.andy.expression.base;

import xyz.lius.andy.expression.*;
import xyz.lius.andy.expression.ast.BracketExpression;
import xyz.lius.andy.expression.ast.CurlyBracketExpression;
import xyz.lius.andy.expression.ast.RoundBracketExpression;

import java.util.List;

/**
 * e.g. left -> right
 */
public class ArrowExpression extends NativeExpression {
    @Override
    public Expression parameters(List<Expression> list) {
        if (list.size() != 2) return ExpressionFactory.error("Error ArrowExpression: " + list);
        BracketExpression left;
        CurlyBracketExpression right;
        if (ExpressionUtils.isSquareBracket(list.get(0)) || ExpressionUtils.isComma(list.get(0))) { //e.g. (x, y, z) -> right
            left = (BracketExpression)list.get(0);
        } else if (ExpressionUtils.isRoundBracket(list.get(0))) {
            BracketExpression roundBracket = (RoundBracketExpression) list.get(0);
            if (roundBracket.list().size() == 1) { //e.g. (x) -> right
                left = ExpressionFactory.squareBracket(roundBracket.list().get(0));
            } else { //e.g. (x y z) -> right
                return ExpressionFactory.error(roundBracket, "Error ArrowExpression!");
            }
        } else { //e.g. x -> right
            left = ExpressionFactory.squareBracket(list.get(0));
        }

        if (ExpressionUtils.isCurlyBracket(list.get(1))) { //e.g. left -> {...}
            right = (CurlyBracketExpression) list.get(1);
        } else { //e.g. left -> x + 1
            right = ExpressionFactory.curlyBracket().add(list.get(1));
        }
        return ExpressionFactory.lambda(left, right);
    }

}
