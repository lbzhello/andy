package xyz.lius.andy.expression.ast;

import xyz.lius.andy.core.Definition;
import xyz.lius.andy.expression.*;

import java.util.Arrays;

/**
 * (f x y)
 */
public class RoundBracketExpression extends BracketExpression {

    public RoundBracketExpression(Expression... expressions) {
        super(expressions);
    }

    @Override
    public Expression[] getParameters() {
        Expression[] params = toArray();
        if (params.length > 1) {
            return Arrays.copyOfRange(params, 1, params.length);
        } else {
            return EMPTY_ELEMENT_DATA;
        }
    }

    @Override
    public Name getName() {
        return get(0).getName();
    }

    @Override
    public String toString() {
        return "(" + super.toString() + ")";
    }

    /**
     * e.g. (f x y)
     * @param context
     * @return
     */
    @Override
    public Expression eval(Context<Name, Expression> context) {
        if (size() == 0) return this; //e.g. ()
        Expression first = get(0).eval(context);
        if (first == ExpressionType.NIL) {
            //查看是否为系统提供的运算符
            first = Definition.getOperator(get(0).toString());
        }

        if (first instanceof Operator) {
            //传参
            for (Expression param : this.getParameters()) {
                ((Operator) first).add(param.eval(context));
            }
            return first.eval(context);
        }

        if (ExpressionUtils.isComplex(first)) { //e.g. name = {...}; (name x y)
            return new StackFrame((Complex) first, context, getParameters()).eval(null);
        } else if (ExpressionUtils.isSquareBracket(first) && size() > 1) { //e.g. name = [...]; name(1)
            Expression index = get(1).eval(context);
            if (!ExpressionUtils.isNumber(index)) return ExpressionFactory.error(index, "Array index should be number.");
            return ExpressionUtils.asSquareBracket(first).get(ExpressionUtils.asNumber(index).intValue());
        } else if (size() == 1) { //e.g. (name)
            return first;
        } else {
            return ExpressionFactory.error(get(0), "Expression must be ComplexExpression!");
        }
    }

}
