package xyz.lbzh.andy.expression.support;

public class StringExpression extends ValueExpression {
    public StringExpression(Object value){
        super(value);
    }

    @Override
    public String toString() {
        return "\"" + this.value.toString() + "\"";
    }

}
