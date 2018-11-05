package xyz.lbzh.andy.expression.ast;

import xyz.lbzh.andy.expression.*;

import java.util.Collections;
import java.util.List;

/**
 * [...]
 */
public class SquareBracketExpression extends BracketExpression {

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
    public Expression map(Expression func) {
        BracketExpression squareBracket = ExpressionFactory.squareBracket();
        Context<Name, Expression> context = new ExpressionContext();
        for (Expression expression : this.list()) {
            context.newbind(ExpressionFactory.symbol("$0"), expression); //put param in the context
            squareBracket.add(func.eval(context));
        }
        return squareBracket;
    }

    public Expression each(Expression func) {
        Context<Name, Expression> context = new ExpressionContext();
        for (Expression expression : this.list()) {
            context.newbind(ExpressionFactory.symbol("$0"), expression);
            func.eval(context);
        }
        return ExpressionType.NIL;
    }

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

    public Expression rest() {
        List<Expression> rst = this.list().size() >= 2 ? this.list().subList(1, this.list().size()) : Collections.emptyList();
        return ExpressionFactory.squareBracket().list(rst);
    }

}
