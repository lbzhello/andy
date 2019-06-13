package xyz.lius.andy.expression.ast;

import xyz.lius.andy.core.Definition;
import xyz.lius.andy.expression.*;
import xyz.lius.andy.expression.context.ExpressionContext;
import xyz.lius.andy.expression.base.ComplexExpression;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * e.g. {...}
 */
@CurlyBracketed
public class CurlyBracketExpression extends BracketExpression {
    private List<Expression> fields = new ArrayList<>();
    private List<Expression> codes = new ArrayList<>();

    public CurlyBracketExpression(Expression... expressions) {
        super(expressions);
    }

    @Override
    public CurlyBracketExpression add(Expression expression) {
        //record the order of the origin file
        super.add(expression);

        if (expression instanceof RoundBracketExpression && (
                Objects.equals(((RoundBracketExpression) expression).get(0), Definition.DEFINE) ||
                Objects.equals(((RoundBracketExpression) expression).get(0), Definition.PAIR))) {
            this.fields.add(expression);
        } else {
            this.codes.add(expression);
        }

        return this;
    }

    @Override
    public ComplexExpression eval(Context<Name, Expression> context) {
        Context<Name, Expression> selfContext = new ExpressionContext(context);
        for (Expression expression : this.fields) {
            expression.eval(selfContext);
        }
        ComplexExpression complex = ExpressionFactory.complex(selfContext).setCodes(this.codes);
        selfContext.add(Definition.SELF, complex);
        return complex;
    }

    @Override
    public String toString() {
        return "{" + super.toString() + "}";
    }

}
