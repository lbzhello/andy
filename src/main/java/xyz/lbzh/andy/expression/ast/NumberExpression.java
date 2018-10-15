package xyz.lbzh.andy.expression.ast;

import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.Name;
import xyz.lbzh.andy.tokenizer.LineNumberToken;

import java.math.BigDecimal;

public class NumberExpression extends BigDecimal implements LineNumberToken, Expression, Name {
    private int lineNumber = 0;

    public NumberExpression(String val) {
        super(val);
    }

    public NumberExpression(String val, int lineNumber) {
        super(val);
        this.lineNumber = lineNumber;
    }

    public NumberExpression(double val) {
        super(val);
    }

    public NumberExpression(double val, int lineNumber) {
        super(val);
        this.lineNumber = lineNumber;
    }

    @Override
    public int getLineNumber() {
        return this.lineNumber;
    }

    @Override
    public Name getName() {
        return this;
    }


}
