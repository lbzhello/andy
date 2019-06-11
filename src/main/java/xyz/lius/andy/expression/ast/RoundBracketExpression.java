package xyz.lius.andy.expression.ast;

import xyz.lius.andy.expression.*;
import xyz.lius.andy.expression.runtime.ComplexExpression;

import java.util.Collections;
import java.util.List;

/**
 * (f x y)
 */
public class RoundBracketExpression extends BracketExpression {

    public RoundBracketExpression(Expression... expressions) {
        super(expressions);
    }

    @Override
    public List<Expression> getParameters() {
        return this.list().size() >= 2 ? this.list().subList(1, this.list().size()) : Collections.emptyList();
    }

    @Override
    public Name getName() {
        return this.first().getName();
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
        if (list().size() == 0) return this; //e.g. ()
        Expression first = first().eval(context);
        if (first == ExpressionType.NIL && first() == ExpressionType.NIL) return ExpressionType.NIL; //e.g. (nil)
        if (ExpressionUtils.isNative(first)) {
            return ExpressionUtils.asNative(first).parameters(this.getParameters()).eval(context);
        }

        if (ExpressionUtils.isComplex(first)) { //e.g. name = {...}; (name x y)
            return new StackFrame((ComplexExpression) first, context, getParameters()).eval(null);
        } else if (ExpressionUtils.isSquareBracket(first) && this.list().size() > 1) { //e.g. name = [...]; name(1)
            Expression index = second().eval(context);
            if (!ExpressionUtils.isNumber(index)) return ExpressionFactory.error(index, "Array index should be number.");
            return ExpressionUtils.asSquareBracket(first).list().get(ExpressionUtils.asNumber(index).intValue());
        } else if (this.list().size() == 1) { //e.g. (name)
            return first;
        } else {
            return ExpressionFactory.error(first(), "Expression must be ComplexExpression!");
        }
    }

}
