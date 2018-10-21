package xyz.lbzh.andy.expression.ast;

import xyz.lbzh.andy.expression.*;

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

    public BracketExpression map(Expression func) {
        BracketExpression squareBracket = ExpressionFactory.squareBracket();
        Context<Name, Expression> context = new ExpressionContext();
        for (Expression expression : this.list()) {
            context.newbind(ExpressionFactory.symbol("$0"), expression);
            squareBracket.add(func.eval(context));
        }
        return squareBracket;
    }

}
