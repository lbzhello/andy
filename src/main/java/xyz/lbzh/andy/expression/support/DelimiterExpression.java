package xyz.lbzh.andy.expression.support;

import xyz.lbzh.andy.expression.Context;
import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.Name;

public class DelimiterExpression extends TokenExpression {

    public DelimiterExpression(Object value) {
        this.value = value;
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        return context.lookup(this);
    }
//    //分隔符是单字符
//    @Override
//    public String toString() {
//        String str = Objects.toString(this.value);
//        int len = str.length();
//        if (len > 1) {
//            return str.substring(len - 1, len);
//        } else {
//            return str;
//        }
//    }
}
