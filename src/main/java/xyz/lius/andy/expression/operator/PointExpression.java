package xyz.lius.andy.expression.operator;

import xyz.lius.andy.core.Definition;
import xyz.lius.andy.core.OperatorSingleton;
import xyz.lius.andy.expression.*;
import xyz.lius.andy.expression.ast.RoundBracketExpression;
import xyz.lius.andy.util.AbstractContainer;

public class PointExpression extends AbstractContainer implements Operator {

    public PointExpression() {
        super(2);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        Expression leftValue = get(0).eval(context);
        Expression rightValue = get(1) instanceof RoundBracketExpression ? get(1).eval(context) : get(1);
        if (TypeCheck.isComplex(leftValue)) { //e.g. get(0) = { name:"liu" age:22 }  get(0).name
            return TypeCheck.asComplex(leftValue).getContext().lookup(rightValue.getName());
        } else if (TypeCheck.isJavaObject(leftValue)) {
            return ExpressionFactory.javaMethod(TypeCheck.asJavaObject(leftValue).getObject(), rightValue.getName().toString());
        } else if (TypeCheck.isSquareBracket(leftValue)) { //e.g. get(0) = [1 2 3 4]  get(0).map
            return ExpressionFactory.arrayMethod(leftValue, rightValue.getName().toString());
        } else if (TypeCheck.isString(leftValue)) {
            return ExpressionFactory.javaMethod(leftValue, rightValue.getName().toString());
        } else if (leftValue == Definition.NIL) {
            return Definition.NIL;
        } else {
            return ExpressionFactory.error(leftValue, "Unsupport operation type!");
        }
    }

    @Override
    public String toString() {
        return get(0).toString() + OperatorSingleton.POINT + get(1).toString();
    }
}
