package xyz.lbzh.andy.expression.ast;


import xyz.lbzh.andy.expression.Context;
import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.ExpressionFactory;
import xyz.lbzh.andy.expression.Name;

public class TemplateExpression extends BracketExpression {

    @Override
    public Expression eval(Context<Name, Expression> context) {
        StringBuffer sb = new StringBuffer();
        for (Expression expression : this.list()) {
            sb.append(expression.eval(context));
        }
        return ExpressionFactory.string(sb.toString());
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (Expression expression : this.list()) {
            sb.append(expression);
        }
        return sb.toString();
    }
}
