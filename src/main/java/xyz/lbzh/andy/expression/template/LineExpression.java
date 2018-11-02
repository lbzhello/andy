package xyz.lbzh.andy.expression.template;


import xyz.lbzh.andy.expression.*;
import xyz.lbzh.andy.expression.ast.BracketExpression;

import java.util.Arrays;
import java.util.List;

/**
 * e.g.
 *     expr = "world"
 *     line = "hello (expr)" => "hello world"
 */
public class LineExpression extends BracketExpression {

    @Override
    public Expression eval(Context<Name, Expression> context) {
        StringBuffer sb = new StringBuffer();
        int charCount = 0, acc = 0;
        for (Expression expression : this.list()) {
            if (ExpressionUtils.isRoundBracket(expression)) { //only eval (...)
                Expression rst = expression.eval(context);
                if (ExpressionUtils.isSquareBracket(rst)) { //if it's a list
                    List<Expression> lines = ExpressionUtils.asSquareBracket(rst).list();
                    if (lines.size() > 1) { //add space to other lines except line 1
                        char[] spaces = new char[charCount];
                        Arrays.fill(spaces, ' ');
                        sb.append(lines.get(0));
                        for (int i = 1; i < lines.size(); i++) {
                            sb.append(String.valueOf(spaces) + lines.get(i));
                        }
                        acc = lines.get(lines.size() - 1).toString().length();
                    } else if (lines.size() == 1){
                        acc = lines.get(0).toString().length();
                        sb.append(lines.get(0));
                    }
                } else {
                    acc = rst.toString().length();
                    sb.append(rst);
                }
            } else {
                acc = expression.toString().length();
                sb.append(expression);
            }
            //record pointer position
            charCount += acc;
        }
        return ExpressionFactory.string(sb.toString());
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (Expression expression : this.list()) {
            sb.append(expression);
        }
        return sb.toString();
    }
}
