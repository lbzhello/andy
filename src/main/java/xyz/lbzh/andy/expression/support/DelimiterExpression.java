package xyz.lbzh.andy.expression.support;

public class DelimiterExpression extends ValueExpression {
    public DelimiterExpression(){}

    public DelimiterExpression(Object value) {
        this.value = value;
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
