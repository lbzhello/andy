package xyz.lius.andy.expression.ast;

import xyz.lius.andy.core.Definition;
import xyz.lius.andy.expression.*;
import xyz.lius.andy.expression.runtime.ComplexExpression;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * e.g. {...}
 */
@CurlyBracketed
public class CurlyBracketExpression extends BracketExpression {
    private List<Expression> fieldList = new ArrayList<>();
    private List<Expression> evalList = new ArrayList<>();

    public CurlyBracketExpression(Expression... expressions) {
        super(expressions);
    }

    @Override
    public CurlyBracketExpression add(Expression expression) {
        //record the order of the origin file
        super.add(expression);

        if (expression instanceof RoundBracketExpression && (
                Objects.equals(((RoundBracketExpression) expression).first(), Definition.DEFINE) ||
                Objects.equals(((RoundBracketExpression) expression).first(), Definition.PAIR))) {
            this.fieldList.add(expression);
        } else {
            this.evalList.add(expression);
        }

        return this;
    }

    @Override
    public ComplexExpression eval(Context<Name, Expression> context) {
        Context<Name, Expression> childChild = new ExpressionContext(context);
        for (Expression expression : this.fieldList) {
            expression.eval(childChild);
        }
//        this.fieldList.stream().forEach(expression -> {
//            expression.eval(childChild);
//        });
        return ExpressionFactory.complex(childChild).list(this.evalList);
    }

    @Override
    public String toString() {
        return "{" + super.toString() + "}";
    }

    private String toStringTest() {
        StringBuffer buildListSB = new StringBuffer();
        StringBuffer evalListSB = new StringBuffer();

        for (Expression expression : fieldList) {
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
