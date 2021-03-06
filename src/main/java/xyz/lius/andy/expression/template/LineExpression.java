package xyz.lius.andy.expression.template;


import xyz.lius.andy.expression.*;
import xyz.lius.andy.expression.ast.BracketExpression;

import java.util.Arrays;

/**
 * e.g.
 *     expr = "world"
 *     line = "hello (expr)" => "hello world"
 */
public class LineExpression extends BracketExpression {

    @Override
    public LineExpression eval(Context<Name, Expression> context) {
        LineExpression line = ExpressionFactory.line();
        Arrays.stream(toArray()).forEach(element -> line.add(element.eval(context)));
        return line;
    }

    public Expression evalOld(Context<Name, Expression> context) {
        StringBuffer sb = new StringBuffer();
        int charCount = 0, acc = 0;
        for (Expression expression : toArray()) {
            if (TypeCheck.isRoundBracket(expression)) { //only eval (...)
                Expression rst = expression.eval(context);
                if (TypeCheck.isSquareBracket(rst)) { //if it's a setCodes
                    BracketExpression lines = TypeCheck.asSquareBracket(rst);
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
        for (Expression expression : toArray()) {
            sb.append(expression);
        }
        return sb.toString();
    }
}
