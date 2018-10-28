package xyz.lbzh.andy.expression.template;

import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.ast.SquareBracketExpression;
import xyz.lbzh.andy.expression.ast.StringExpression;

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
        for (Expression line : list()) {
            sb.append(line + "\n");
        }
        return sb.toString();
    }
}
