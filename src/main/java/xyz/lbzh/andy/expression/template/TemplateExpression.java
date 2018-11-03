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
    private List<Expression> lines = new LinkedList<>();

    public void addLine(Expression line) {
        this.lines.add(line);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {

        return relative(context);
    }

    /**
     * 首行和末行如果是空行则忽略,其余像左平移
     * @param context
     * @return
     */
    private Expression relative(Context<Name, Expression> context) {
        LinesExpression linesExpression = new LinesExpression();

        if (this.lines.size() > 1) {
            String first = this.lines.get(0).eval(context).toString();
            String second = this.lines.get(1).eval(context).toString(); //record relative
            //求出左边得空格数目
            int offset = second.length() + 1 - (second + "!").trim().length();
            String last = this.lines.get(this.lines.size() - 1).eval(context).toString();
            if (!first.isBlank()) linesExpression.add(ExpressionFactory.string(first));
            int index = 1;
            while (index < this.lines.size() - 1) {
                String lineStr = lines.get(index++).eval(context).toString();
                linesExpression.add(ExpressionFactory.string(lineStr.substring(offset, lineStr.length())));
            }
            if (!last.isBlank()) linesExpression.add(ExpressionFactory.string(last));
        } else if (this.lines.size() == 1){
            linesExpression.add(ExpressionFactory.string(this.lines.get(0).eval(context).toString()));
        } else {
            return ExpressionFactory.error(this, "Template should not empty!");
        }
        return linesExpression;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (Expression expression : lines) {
            sb.append(expression);
        }
        return sb.toString();
    }
}
