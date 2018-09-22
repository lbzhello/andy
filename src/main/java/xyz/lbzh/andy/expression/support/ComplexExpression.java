package xyz.lbzh.andy.expression.support;

import xyz.lbzh.andy.core.Definition;
import xyz.lbzh.andy.expression.Expression;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 表达式上下文
 * e.g. {(...) (...) (...) ...}
 */
public class ComplexExpression extends DefaultExpression {
    private List<Expression> buildStream = new ArrayList<>();
    private List<Expression> evalStream = new ArrayList<>();

    public DefaultExpression add(Expression expression) {
        //record the order of the origin file
        super.add(expression);

        if (expression instanceof SExpression) {
            SExpression sexpression = (SExpression)expression;
            if (Objects.equals(sexpression.first(), Definition.DEFINE)) { //对象定义
                this.addBuildStream(expression);
            } else if (Objects.equals(sexpression.first(), Definition.PAIR)) { //字段定义
                this.addBuildStream(expression);
            } else {
                this.addEvalStream(expression);
            }
        } else {
            this.addEvalStream(expression);
        }

        return this;
    }

    private void addBuildStream(Expression expression){
        this.buildStream.add(expression);
    }

    private void addEvalStream(Expression expression){
        this.evalStream.add(expression);
    }

    @Override
    public String toString() {
        return "{" + super.toString() + "}";
    }

    public String toString_old() {
        StringBuffer buildStreamSB = new StringBuffer();
        StringBuffer evalStreamSB = new StringBuffer();

        for (Expression expression : buildStream) {
            buildStreamSB.append(expression + " ");
        }

        for (Expression expression : evalStream) {
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
