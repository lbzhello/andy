package xyz.lbzh.andy.expression.support;

import java.math.BigDecimal;

public class NumberExpression extends TokenExpression {
    BigDecimal value;
    public NumberExpression(BigDecimal value) {
        super.value = value; //for toString()
        this.value = value;
    }

    @Override
    public BigDecimal getValue() {
        return this.value;
    }
}
