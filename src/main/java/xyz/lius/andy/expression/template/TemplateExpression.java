package xyz.lius.andy.expression.template;

import xyz.lius.andy.expression.*;
import xyz.lius.andy.util.AbstractContainer;

import java.util.*;

/**
 * e.g.
 *   line
 *   line
 *   line
 *   ...
 * @see LineExpression
 */
public class TemplateExpression extends AbstractContainer implements Expression {

    @Override
    public Expression eval(Context<Name, Expression> context) {
        TemplateExpression template = ExpressionFactory.template();
        Arrays.stream(toArray()).forEach(line ->  {
            template.add(line.eval(context));
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

        if (size() > 1) {
            String first = get(0).eval(context).toString();
            String second = get(1).eval(context).toString(); //record relative
            //求出左边得空格数目
            int offset = second.length() + 1 - (second + "!").trim().length();
            String last = get(size() - 1).eval(context).toString();
            if (!first.isBlank()) linesExpression.add(ExpressionFactory.string(first));
            int index = 1;
            while (index < size() - 1) {
                String lineStr = moveLine(get(index++), offset).eval(context).toString();
                linesExpression.add(ExpressionFactory.string(lineStr));
            }
            if (!last.isBlank()) linesExpression.add(ExpressionFactory.string(last));
        } else if (size() == 1){
            linesExpression.add(ExpressionFactory.string(get(0).eval(context).toString()));
        } else {
            return ExpressionFactory.error(this, "Template should not empty!");
        }
        return linesExpression;
    }

    //向左平移offset单位
    private Expression moveLine(Expression expression, int offset) {
        if (expression instanceof LineExpression && offset > 0) {
            Expression[] array = ((LineExpression) expression).toArray();
            if (array.length > 0 && TypeCheck.isString(array[0])) {
                String str = array[0].toString();
                array[0] = ExpressionFactory.string(str.substring(offset, str.length()));
            }
        }
        return expression;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (Expression expression : toArray()) {
            sb.append(expression);
        }
        return sb.toString();
    }
}
