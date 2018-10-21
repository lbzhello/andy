package xyz.lbzh.andy.expression.ast;

import xyz.lbzh.andy.expression.*;
import xyz.lbzh.andy.expression.runtime.MethodExpression;
import xyz.lbzh.andy.expression.runtime.ComplexExpression;
import xyz.lbzh.andy.expression.runtime.NativeExpression;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.ArrayList;
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
        if (name == ExpressionType.NIL) return ExpressionType.NIL;
        if (name instanceof NativeExpression) {
            return ((NativeExpression) name).parameters(this.getParameters()).eval(context);
        }
        if (name instanceof ComplexExpression) { //e.g. name = {...} (name x y)
            Context<Name, Expression> childContext = new ExpressionContext();
            //put args in context
            for (int i = 0; i < this.getParameters().size(); i++) {
                childContext.bind(ExpressionFactory.symbol("$" + i), this.tail().get(i).eval(context));
            }
            return name.eval(childContext);
        } else if (this.list().size() == 1) { //e.g. (name)
            return name;
        } else {
            return ExpressionFactory.error(first(), "Expression must be ComplexExpression!");
        }
    }

}
