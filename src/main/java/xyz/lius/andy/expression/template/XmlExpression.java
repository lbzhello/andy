package xyz.lius.andy.expression.template;

import xyz.lius.andy.expression.Context;
import xyz.lius.andy.expression.Expression;
import xyz.lius.andy.expression.ExpressionFactory;
import xyz.lius.andy.expression.Name;
import xyz.lius.andy.expression.ast.BracketExpression;


public class XmlExpression implements Expression {
    private XmlTagExpression startTag = ExpressionFactory.xmlTag();
    private BracketExpression body = ExpressionFactory.bracket();
    private XmlTagExpression closeTag = ExpressionFactory.xmlTag();

    @Override
    public Expression eval(Context<Name, Expression> context) {
        XmlExpression xml = ExpressionFactory.xml();
        BracketExpression bracket = ExpressionFactory.squareBracket();
        for (Expression expression : body.list()) {
            bracket.add(expression.eval(context));
        }
        xml.setStartTag(this.startTag.eval(context));
        xml.setBody(bracket);
        xml.setCloseTag(this.closeTag.eval(context));
        return xml;
    }

    public void setStartTag(XmlTagExpression startTag) {
        this.startTag = startTag;
    }

    public void setBody(BracketExpression body) {
        this.body = body;
    }

    public void setCloseTag(XmlTagExpression closeTag) {
        this.closeTag = closeTag;
    }

    public XmlTagExpression getStartTag() {
        return startTag;
    }

    public BracketExpression getBody() {
        return body;
    }

    public XmlTagExpression getCloseTag() {
        return closeTag;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (Expression expression : body.list()) {
            sb.append(expression);
        }
        return this.startTag.toString() + sb.toString() + this.closeTag.toString();
    }
}
