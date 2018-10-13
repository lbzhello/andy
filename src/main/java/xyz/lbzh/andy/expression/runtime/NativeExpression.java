package xyz.lbzh.andy.expression.runtime;

import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.ExpressionType;
import xyz.lbzh.andy.expression.ast.RoundBracketExpression;

import java.util.List;

public class NativeExpression extends RoundBracketExpression {
    public Expression build(List<Expression> list) {
        return ExpressionType.NIL;
    }
}
