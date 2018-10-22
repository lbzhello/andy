package xyz.lbzh.andy.expression.runtime;

import xyz.lbzh.andy.expression.*;
import xyz.lbzh.andy.expression.ast.RoundBracketExpression;
import xyz.lbzh.andy.expression.ast.SquareBracketExpression;
import xyz.lbzh.andy.tokenizer.Token;
import xyz.lbzh.andy.tokenizer.TokenFlag;

import java.util.List;

public class PointExpression extends RoundBracketExpression {
    private Expression left;
    private Expression right;
    
    public PointExpression(Expression left, Expression right) {
        super(TokenFlag.POINT, left, right);
        this.left = left;
        this.right = right;
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        Expression leftValue = left.eval(context);
        Expression rightValue = right instanceof RoundBracketExpression ? right.eval(context) : right;
        if (leftValue instanceof ComplexExpression) { //e.g. left = { name:"liu" age:22 }  left.name
            return ((ComplexExpression) leftValue).getContext().lookup(rightValue.getName());
        } else if (ExpressionUtils.isSquareBracket(leftValue)) { //e.g. left = [1 2 3 4]  left.map
            MethodExpression methodExpression = new MethodExpression(leftValue);
            methodExpression.setMethodName(rightValue.getName().toString());
            return methodExpression;
        } else if (ExpressionUtils.isMethod(leftValue)) { //java class call
            MethodExpression methodExpression = (MethodExpression)leftValue;
            methodExpression.setMethodName(rightValue.getName().toString());
            return methodExpression;
        } else if (leftValue == ExpressionType.NIL){
            return ExpressionType.NIL;
        } else {
            return ExpressionFactory.error(leftValue, "Unsupport operation type!");
        }
    }
}
