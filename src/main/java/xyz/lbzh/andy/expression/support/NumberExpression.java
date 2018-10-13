package xyz.lbzh.andy.expression.support;

import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.Name;
import xyz.lbzh.andy.tokenizer.Token;

import java.math.BigDecimal;

public class NumberExpression extends BigDecimal implements Token, Expression, Name {

    public NumberExpression(String val) {
        super(val);
    }

    public NumberExpression(double val) {
        super(val);
    }

    @Override
    public Name getName() {
        return this;
    }


}
