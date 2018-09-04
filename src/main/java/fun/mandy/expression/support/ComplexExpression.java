package fun.mandy.expression.support;

import fun.mandy.expression.Expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 一组表达式集合
 * e.g. {(...) (...) (...) ...}
 */
public class ComplexExpression implements Expression {
    private List<Expression> buildStream = new ArrayList<>();
    private List<Expression> evalStream = new ArrayList<>();

    public void addBuildStream(Expression expression){
        this.buildStream.add(expression);
    }

    public void addEvalStream(Expression expression){
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
