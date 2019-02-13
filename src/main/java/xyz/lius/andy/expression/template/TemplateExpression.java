package xyz.lius.andy.expression.template;

import xyz.lius.andy.expression.*;

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
        TemplateExpression template = ExpressionFactory.template();
        this.lines.stream().forEach(line ->  {
            template.addLine(line.eval(context));
        });
        return template;
//        return relative(context);
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
                String lineStr = moveLine(lines.get(index++), offset).eval(context).toString();
                linesExpression.add(ExpressionFactory.string(lineStr));
            }
            if (!last.isBlank()) linesExpression.add(ExpressionFactory.string(last));
        } else if (this.lines.size() == 1){
            linesExpression.add(ExpressionFactory.string(this.lines.get(0).eval(context).toString()));
        } else {
            return ExpressionFactory.error(this, "Template should not empty!");
        }
        return linesExpression;
    }

    //向左平移offset单位
    private Expression moveLine(Expression expression, int offset) {
        if (expression instanceof LineExpression && offset > 0) {
            List<Expression> list = ((LineExpression) expression).list();
            if (list.size() > 0 && ExpressionUtils.isString(list.get(0))) {
                String str = list.get(0).toString();
                list.set(0, ExpressionFactory.string(str.substring(offset, str.length())));
            }
        }
        return expression;
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
