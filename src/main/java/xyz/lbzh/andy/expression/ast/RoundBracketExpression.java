package xyz.lbzh.andy.expression.ast;

import xyz.lbzh.andy.expression.*;

import java.lang.invoke.MethodHandle;
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
        if (list().size() == 0) return ExpressionType.NIL; //e.g. ()
        Expression name = first().eval(context);
        if (name == ExpressionType.NIL && first() == ExpressionType.NIL) return ExpressionType.NIL; //e.g. (nil)
        if (ExpressionUtils.isNative(name)) {
            return ExpressionUtils.asNative(name).parameters(this.getParameters()).eval(context);
        }

        if (ExpressionUtils.isComplex(name)) { //e.g. name = {...}; (name x y)
            Context<Name, Expression> childContext = new ExpressionContext();
            //put args in context
            for (int i = 0; i < this.getParameters().size(); i++) {
                childContext.bind(ExpressionFactory.symbol("$" + i), this.getParameters().get(i).eval(context));
            }
            return name.eval(childContext);
        } else if (ExpressionUtils.isSquareBracket(name)) { //e.g. name = [...]; name(1)
            Expression index = second().eval(context);
            if (!ExpressionUtils.isNumber(index)) return ExpressionFactory.error(index, "Array index should be number.");
            return ExpressionUtils.asSquareBracket(name).list().get(ExpressionUtils.asNumber(index).intValue());
        } else if (this.list().size() == 1) { //e.g. (name)
            return name;
        } else {
            return ExpressionFactory.error(first(), "Expression must be ComplexExpression!");
        }
    }

}
