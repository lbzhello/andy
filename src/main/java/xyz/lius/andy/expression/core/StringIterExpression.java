package xyz.lius.andy.expression.core;

import xyz.lius.andy.core.Definition;
import xyz.lius.andy.expression.Expression;
import xyz.lius.andy.expression.ExpressionFactory;
import xyz.lius.andy.expression.ast.StringExpression;

/**
 * String 迭代类比 SquareBracketExpression 节省空间
 */
public class StringIterExpression extends StringExpression {
    private String value;

    private int pos;
    private int end;
    private static final int DONE = '\uFFFF';

    //不用建立多个对象
    private static final StringExpression singleton = ExpressionFactory.string("");

    public StringIterExpression(String value) {
        super(value);
        this.value = value;
        this.pos = 0;
        this.end = value.length() - 1;
    }


    public Expression previous() {
        pos--;
        if (pos >= 0 && pos <= end) {
            singleton.setValue(String.valueOf(value.charAt(pos)));
            return singleton;
        } else {
            return Definition.NIL;
        }
    }

    public Expression current() {
        if (pos >= 0 && pos <= end) {
            singleton.setValue(String.valueOf(value.charAt(pos)));
            return singleton;
        } else {
            return Definition.NIL;
        }
    }

    public Expression next() {
        pos++;
        if (pos >= 0 && pos <= end) {
            singleton.setValue(String.valueOf(value.charAt(pos)));
            return singleton;
        } else {
            return Definition.NIL;
        }

    }

}
