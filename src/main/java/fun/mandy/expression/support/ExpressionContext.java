package fun.mandy.expression.support;

import fun.mandy.core.Definition;
import fun.mandy.expression.Expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 表达式上下文
 * e.g. {(...) (...) (...) ...}
 */
public class ExpressionContext implements Expression {
    private List<Expression> buildStream = new ArrayList<>();
    private List<Expression> evalStream = new ArrayList<>();

    public void add(Expression expression) {
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
    }

    private void addBuildStream(Expression expression){
        this.buildStream.add(expression);
    }

    private void addEvalStream(Expression expression){
        this.evalStream.add(expression);
    }

    @Override
    public String toString() {
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
