package xyz.lbzh.andy.expression.support;

import xyz.lbzh.andy.core.Definition;
import xyz.lbzh.andy.expression.Context;
import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.ExpressionContext;
import xyz.lbzh.andy.expression.Name;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * e.g. {...}
 */
public class CurlyBracketExpression extends BracketExpression {
    private List<Expression> buildList = new ArrayList<>();
    private List<Expression> evalList = new ArrayList<>();

    private Context<Name, Object> context = new ExpressionContext();

    public CurlyBracketExpression(Expression... expressions) {
        super(expressions);
    }

    public BracketExpression add(Expression expression) {
        //record the order of the origin file
        super.add(expression);

        if (expression instanceof RoundBracketExpression) {
            RoundBracketExpression roundBracketExpression = (RoundBracketExpression)expression;
            if (Objects.equals(roundBracketExpression.first(), Definition.DEFINE)) { //对象定义
                this.buildList.add(expression);
            } else if (Objects.equals(roundBracketExpression.first(), Definition.PAIR)) { //字段定义
                this.buildList.add(expression);
            } else {
                this.evalList.add(expression);
            }
        } else {
            this.evalList.add(expression);
        }

        return this;
    }

    public CurlyBracketExpression parent(Context<Name, Object> context) {
        this.context.parent(context);
        return this;
    }

    @Override
    public String toString() {
        return "{" + super.toString() + "}";
    }

    public String show() {
        StringBuffer buildListSB = new StringBuffer();
        StringBuffer evalListSB = new StringBuffer();

        for (Expression expression : buildList) {
            buildListSB.append(expression + " ");
        }

        for (Expression expression : evalList) {
            evalListSB.append(expression + " ");
        }

        if (buildListSB.length() > 0 && evalListSB.length() == 0) { //去掉 buildStreamSB 最后空格
            buildListSB.deleteCharAt(buildListSB.length() - 1);
        }

        if (evalListSB.length() > 0) { //去掉evalStreamSB最后空格
            evalListSB.deleteCharAt(evalListSB.length() - 1);
        }

        return "{" + buildListSB + evalListSB + "}";
    }
}
