package xyz.lbzh.andy.expression.runtime;

import xyz.lbzh.andy.expression.*;
import xyz.lbzh.andy.expression.ast.BracketExpression;
import xyz.lbzh.andy.expression.ast.CurlyBracketExpression;

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
        if (ExpressionUtils.isBracket(list.get(0))) { //e.g. (x) -> right
            left = (BracketExpression)list.get(0);
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
