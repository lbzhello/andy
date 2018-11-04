package xyz.lbzh.andy.expression.template;

import xyz.lbzh.andy.expression.Context;
import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.ExpressionFactory;
import xyz.lbzh.andy.expression.Name;
import xyz.lbzh.andy.expression.ast.BracketExpression;

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
        for (Expression expression : this.list()) {
            xmlTag.add(expression.eval(context));
        }
        return xmlTag;
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
