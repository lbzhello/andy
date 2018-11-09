package xyz.lbzh.andy.expression.ast;

import xyz.lbzh.andy.expression.*;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * [...]
 */
public class SquareBracketExpression extends BracketExpression implements ExpressionArray {

    public SquareBracketExpression(Expression... expressions) {
        super(expressions);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        BracketExpression rstList = ExpressionFactory.squareBracket();
        for (Expression element : list()) {
            rstList.add(element.eval(context));
        }
        return rstList;
    }

    @Override
    public String toString() {
        return "[" + super.toString() + "]";
    }

    //list operation
    @Override
    public Expression map(Expression func) {
        BracketExpression squareBracket = ExpressionFactory.squareBracket();
        Context<Name, Expression> context = new ExpressionContext();
        for (Expression expression : this.list()) {
            context.newbind(ExpressionFactory.symbol("$0"), expression); //put param in the context
            squareBracket.add(func.eval(context));
        }
        return squareBracket;
    }

    @Override
    public Expression each(Expression func) {
        Context<Name, Expression> context = new ExpressionContext();
        for (Expression expression : this.list()) {
            context.newbind(ExpressionFactory.symbol("$0"), expression);
            func.eval(context);
        }
        return ExpressionType.NIL;
    }

    @Override
    public Expression filter(Expression func) {
        BracketExpression squareBracket = ExpressionFactory.squareBracket();
        Context<Name, Expression> context = new ExpressionContext();
        for (Expression expression : this.list()) {
            context.newbind(ExpressionFactory.symbol("$0"), expression);
            if (func.eval(context) == ExpressionType.TRUE) {
                squareBracket.add(expression);
            }
        }
        return squareBracket;
    }

    @Override
    public Expression reduce(Expression func) {
        Context<Name, Expression> context = new ExpressionContext();
        Iterator<Expression> iterator = list().iterator();
        if (iterator.hasNext()) {
            Expression first = iterator.next();
            context.newbind(ExpressionFactory.symbol("$0"), first);
            if (iterator.hasNext()) {
                return reduceCircle(iterator, func, context);
            } else { //e.g. [fist]
                return first;
            }
        } else { //e.g. []
            return ExpressionType.NIL;
        }

    }

    /**
     *
     * @param iterator next expression
     * @param func  reduce function
     * @param context avoid create unnecessary contexts
     * @return
     */
    private Expression reduceCircle(Iterator<Expression> iterator, Expression func, Context<Name, Expression> context) {
        context.newbind(ExpressionFactory.symbol("$1"), iterator.next()); //绑定第二个参数
        Expression first = func.eval(context);
        if (iterator.hasNext()) {
            context.newbind(ExpressionFactory.symbol("$0"), first);
            return reduceCircle(iterator, func, context);
        } else {
            return first;
        }

    }

    @Override
    public Expression rest() {
        List<Expression> rst = this.list().size() >= 2 ? this.list().subList(1, this.list().size()) : Collections.emptyList();
        return ExpressionFactory.squareBracket().list(rst);
    }

    @Override
    public Expression reverse() {
        BracketExpression squareBracket = ExpressionFactory.squareBracket();
        for (int i = list().size() - 1; i >= 0; i--) {
            squareBracket.add(list().get(i));
        }
        return squareBracket;
    }

    @Override
    public Expression count() {
        return ExpressionFactory.number(list().size());
    }
}
