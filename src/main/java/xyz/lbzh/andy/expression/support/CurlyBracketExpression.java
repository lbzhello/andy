package xyz.lbzh.andy.expression.support;

import xyz.lbzh.andy.core.Definition;
import xyz.lbzh.andy.expression.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * e.g. {...}
 */
public class CurlyBracketExpression extends BracketExpression {
    private List<Expression> buildList = new ArrayList<>();
    private List<Expression> evalList = new ArrayList<>();

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

    public Context<Name, Object> build(Context<Name, Object> context) {
        this.buildList.stream().forEach(expression -> {
            expression.eval(context);
        });
        return context;
    }

    @Override
    public Expression eval(Context<Name, Object> context) {
        Expression rst = ExpressionType.NIL;
        for (Expression expression : this.evalList) {
            rst = expression.eval(context);
        }
        return rst;
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
