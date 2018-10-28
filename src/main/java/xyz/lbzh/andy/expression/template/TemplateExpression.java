package xyz.lbzh.andy.expression.template;

import xyz.lbzh.andy.expression.Context;
import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.ExpressionFactory;
import xyz.lbzh.andy.expression.Name;

import java.util.*;

/**
 * e.g.
 *   line
 *   line
 *   line
 *   ...
 * @see LineExpression
 */
public class TemplateExpression implements Expression {
    private List<LineExpression> lines = new LinkedList<>();

    public void addLine(LineExpression line) {
        this.lines.add(line);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        LinesExpression linesExpression = new LinesExpression();
        for (LineExpression line : this.lines) {
            linesExpression.add(ExpressionFactory.string(line.eval(context) + "\n"));
        }
        return linesExpression;
    }

}
