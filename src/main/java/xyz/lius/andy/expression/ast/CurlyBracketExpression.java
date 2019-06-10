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
    private List<Expression> data = new ArrayList<>();
    private List<Expression> code = new ArrayList<>();

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
            this.data.add(expression);
        } else {
            this.code.add(expression);
        }

        return this;
    }

    @Override
    public ComplexExpression eval(Context<Name, Expression> context) {
        Context<Name, Expression> childContext = new ExpressionContext(context);
        for (Expression expression : this.data) {
            expression.eval(childContext);
        }
        return ExpressionFactory.complex(childContext).code(this.code);
    }

    @Override
    public String toString() {
        return "{" + super.toString() + "}";
    }

}
