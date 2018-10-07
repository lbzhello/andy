package xyz.lbzh.andy.expression.function;

import xyz.lbzh.andy.expression.*;
import xyz.lbzh.andy.expression.internal.ErrorExpression;
import xyz.lbzh.andy.expression.support.NumberExpression;
import xyz.lbzh.andy.expression.support.RoundBracketExpression;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@RoundBracketed
public class PlusExpression extends RoundBracketExpression {
    private List<Expression> parameters;

    //e.g. (+ 2 5)
    public PlusExpression(Expression... expressions) {
        super(expressions);
        parameters = Arrays.asList(expressions).subList(1, expressions.length);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        BigDecimal accu = BigDecimal.ZERO;
        for (Expression expression : parameters) {
            Expression factor = expression.eval(context);
            if (!(factor instanceof NumberExpression)) return new ErrorExpression("Unsupport Operand Type!");
            accu = accu.add(((NumberExpression) factor).getValue());
        }
        return new NumberExpression(accu);
    }

    //    Expression left;
//    Expression right;
//
//    public PlusExpression(Expression left, Expression right) {
//        super(ExpressionType.PLUS, left, right);
//        this.left = left;
//        this.right = right;
//    }
//
//    @Override
//    public Expression eval(Context<Name, Expression> context) {
//        Expression leftExpression = left.eval(context);
//        Expression rightExpression = right.eval(context);
//        if (leftExpression instanceof StringExpression || rightExpression instanceof StringExpression) {
//            return new StringExpression(leftExpression.toString() + rightExpression.toString());
//        }
//        if (!(leftExpression instanceof NumberExpression) || !(rightExpression instanceof NumberExpression)) {
//            return new ErrorExpression("Unsupport Operand Type!");
//        }
//
//        BigDecimal leftValue =  ((NumberExpression) leftExpression).getValue();
//        BigDecimal rightValue = ((NumberExpression) rightExpression).getValue();
//
//
//        return new NumberExpression(leftValue.add(rightValue));
//    }

    @Override
    public Expression shift() {
        return ExpressionFactory.roundBracket();
    }
}
