package fun.mandy.expression.support;

import fun.mandy.expression.Context;
import fun.mandy.expression.Expression;
import fun.mandy.expression.Name;

public class StringExpression extends ObjectExpression implements Name {
    public  StringExpression(){}
    public StringExpression(Object value){
        super(value);
    }
    @Override
    public Expression eval(Context<Name, Expression> context) {
        return new StringExpression(value);
    }

    @Override
    public String toString() {
        return "\"" + this.value.toString() + "\"";
    }
}
