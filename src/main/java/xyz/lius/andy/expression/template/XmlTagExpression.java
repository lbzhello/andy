package xyz.lius.andy.expression.template;

import xyz.lius.andy.expression.Context;
import xyz.lius.andy.expression.Expression;
import xyz.lius.andy.expression.ExpressionFactory;
import xyz.lius.andy.expression.Name;
import xyz.lius.andy.expression.ast.BracketExpression;

public class XmlTagExpression extends BracketExpression {
    private boolean closeFlag = false; //is a close lable like </book>

    public XmlTagExpression(Expression... expressions) {
        super(expressions);
    }

    public boolean isClose() {
        return closeFlag;
    }

    public void setClose(boolean closeFlag) {
        this.closeFlag = closeFlag;
    }

    @Override
    public XmlTagExpression eval(Context<Name, Expression> context) {
        XmlTagExpression xmlTag = ExpressionFactory.xmlTag();
        for (Expression expression : toArray()) {
            xmlTag.add(expression.eval(context));
        }
        return xmlTag;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (Expression expression : toArray()) {
            sb.append(expression);
        }
        return sb.toString();
    }
}
