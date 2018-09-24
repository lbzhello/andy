package xyz.lbzh.andy.expression.support;

import xyz.lbzh.andy.core.Definition;
import xyz.lbzh.andy.expression.Expression;

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
            RoundBracketExpression sexpression = (RoundBracketExpression)expression;
            if (Objects.equals(sexpression.first(), Definition.DEFINE)) { //对象定义
                this.addBuildList(expression);
            } else if (Objects.equals(sexpression.first(), Definition.PAIR)) { //字段定义
                this.addBuildList(expression);
            } else {
                this.addEvalList(expression);
            }
        } else {
            this.addEvalList(expression);
        }

        return this;
    }

    private void addBuildList(Expression expression){
        this.buildList.add(expression);
    }

    private void addEvalList(Expression expression){
        this.evalList.add(expression);
    }

    @Override
    public String toString() {
        return "{" + super.toString() + "}";
    }

    public String toString_old() {
        StringBuffer buildStreamSB = new StringBuffer();
        StringBuffer evalStreamSB = new StringBuffer();

        for (Expression expression : buildList) {
            buildStreamSB.append(expression + " ");
        }

        for (Expression expression : evalList) {
            evalStreamSB.append(expression + " ");
        }

        if (buildStreamSB.length() > 0 && evalStreamSB.length() == 0) { //去掉 buildStreamSB 最后空格
            buildStreamSB.deleteCharAt(buildStreamSB.length() - 1);
        }

        if (evalStreamSB.length() > 0) { //去掉evalStreamSB最后空格
            evalStreamSB.deleteCharAt(evalStreamSB.length() - 1);
        }

        return "{" + buildStreamSB + evalStreamSB + "}";
    }
}