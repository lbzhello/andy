package xyz.lbzh.andy.expression;

import xyz.lbzh.andy.expression.support.BracketExpression;
import xyz.lbzh.andy.expression.support.CurlyBracketExpression;
import xyz.lbzh.andy.expression.support.RoundBracketExpression;
import xyz.lbzh.andy.expression.support.SquareBracketExpression;

public class ExpressionBuilder {
    public static BracketExpression bracket(Expression... expressions) {
        return new BracketExpression(expressions);
    }

    public static BracketExpression roundBracket(Expression... expressions) {
        return new RoundBracketExpression(expressions);
    }

    public static BracketExpression squareBracket(Expression...expressions) {
        return new SquareBracketExpression(expressions);
    }

    public static BracketExpression curlyBracket(Expression... expressions) {
        return new CurlyBracketExpression();
    }

    public static BracketExpression operator(Expression... expressions) {
        return RoundBracketExpression.operator(expressions);
    }

    public static BracketExpression command(Expression... expressions) {
        return RoundBracketExpression.command(expressions);
    }
}
