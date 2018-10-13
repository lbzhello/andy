package xyz.lbzh.andy.expression.support;

public class StringExpression extends TokenExpression {
    public StringExpression(String value){
        super(value);
    }

    @Override
    public String toString() {
        return "\"" + this.value.toString() + "\"";
    }

}
