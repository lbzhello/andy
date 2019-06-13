package xyz.lius.andy.expression.base;

import xyz.lius.andy.expression.*;
import xyz.lius.andy.expression.ast.RoundBracketExpression;
import xyz.lius.andy.compiler.tokenizer.TokenFlag;

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
        if (ExpressionUtils.isComplex(leftValue)) { //e.g. left = { name:"liu" age:22 }  left.name
            return ExpressionUtils.asComplex(leftValue).getContext().lookup(rightValue.getName());
        } else if (ExpressionUtils.isJavaObject(leftValue)) {
            return ExpressionFactory.javaMethod(ExpressionUtils.asJavaObject(leftValue).getObject(), rightValue.getName().toString());
        } else if (ExpressionUtils.isSquareBracket(leftValue)) { //e.g. left = [1 2 3 4]  left.map
            return ExpressionFactory.arrayMethod(leftValue, rightValue.getName().toString());
        } else if (ExpressionUtils.isString(leftValue)) {
            return ExpressionFactory.javaMethod(leftValue, rightValue.getName().toString());
        } else if (leftValue == ExpressionType.ARRAY) { //e.g. Array.fromString()
            return ExpressionFactory.error(leftValue, "Not Realized");
        } else if (leftValue == ExpressionType.NIL) {
            return ExpressionType.NIL;
        } else {
            return ExpressionFactory.error(leftValue, "Unsupport operation type!");
        }
    }

    @Override
    public String toString() {
        return left.toString() + "." + right.toString();
    }
}