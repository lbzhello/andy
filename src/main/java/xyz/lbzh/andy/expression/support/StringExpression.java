package xyz.lbzh.andy.expression.support;

import xyz.lbzh.andy.expression.Name;

public class StringExpression extends ValueExpression implements Name {
    public StringExpression(Object value){
        super(value);
    }

    @Override
    public String toString() {
        return "\"" + this.value.toString() + "\"";
    }

}
