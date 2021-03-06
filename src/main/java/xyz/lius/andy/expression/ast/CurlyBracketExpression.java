package xyz.lius.andy.expression.ast;

import xyz.lius.andy.core.Definition;
import xyz.lius.andy.expression.*;
import xyz.lius.andy.expression.context.ExpressionContext;
import xyz.lius.andy.expression.operator.ColonExpression;
import xyz.lius.andy.expression.operator.DefineExpression;

import java.util.ArrayList;
import java.util.List;

/**
 * e.g. {...}
 */
public class CurlyBracketExpression extends BracketExpression {
    private List<Expression> fields = new ArrayList<>();
    private List<Expression> codes = new ArrayList<>();

    public CurlyBracketExpression(Expression... expressions) {
        super(expressions);
    }

    @Override
    public void add(Expression expression) {
        //record the order of the origin file
        super.add(expression);

        if (expression instanceof DefineExpression || expression instanceof ColonExpression) {
            this.fields.add(expression);
        } else {
            this.codes.add(expression);
        }

    }

    @Override
    public Complex eval(Context<Name, Expression> context) {
        Context<Name, Expression> selfContext = new ExpressionContext(context);
        for (Expression expression : this.fields) {
            expression.eval(selfContext);
        }
        Complex complex = ExpressionFactory.complex(selfContext);
        complex.setCodes(this.codes.toArray(new Expression[this.codes.size()]));
        selfContext.add(Definition.SELF, complex);
        return complex;
    }

    @Override
    public String toString() {
        return "{" + super.toString() + "}";
    }

}
