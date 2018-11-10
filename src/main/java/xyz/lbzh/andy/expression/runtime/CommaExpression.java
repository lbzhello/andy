package xyz.lbzh.andy.expression.runtime;

import xyz.lbzh.andy.expression.Context;
import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.ExpressionFactory;
import xyz.lbzh.andy.expression.Name;
import xyz.lbzh.andy.expression.ast.BracketExpression;
import xyz.lbzh.andy.expression.ast.RoundBracketExpression;

import java.util.List;

public class CommaExpression extends RoundBracketExpression {
    public CommaExpression(Expression... expressions) {
        super(expressions);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        BracketExpression squareBracket = ExpressionFactory.squareBracket();
        for (Expression expression : list()) {
            squareBracket.add(expression.eval(context));
        }
        return squareBracket;
    }

    @Override
    public List<Expression> getParameters() {
        return list();
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        if (list() != null && list().size() > 0) {
            for (Expression expression : list()) {
                sb.append(expression + ", ");
            }
            //remove the last space and comma
            sb.replace(sb.length()-2, sb.length(), "");
        }
        return "(" + sb.toString() + ")";
    }
}