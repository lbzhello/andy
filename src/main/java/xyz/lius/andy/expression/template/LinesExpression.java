package xyz.lius.andy.expression.template;

import xyz.lius.andy.expression.Expression;
import xyz.lius.andy.expression.ast.SquareBracketExpression;
import xyz.lius.andy.expression.ast.StringExpression;

import java.util.List;

/**
 * use to receive result of TemplateExpression
 *
 * @see TemplateExpression
 */
public class LinesExpression extends SquareBracketExpression {

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (Expression line : toArray()) {
            sb.append(line);
        }
        return sb.toString();
    }
}
