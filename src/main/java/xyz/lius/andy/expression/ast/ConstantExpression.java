package xyz.lius.andy.expression.ast;

import xyz.lius.andy.expression.Context;
import xyz.lius.andy.expression.Expression;
import xyz.lius.andy.expression.Name;
import xyz.lius.andy.compiler.tokenizer.LineNumberToken;

public class ConstantExpression implements LineNumberToken, Expression, Name {
    protected Object value;
    protected int lineNumber = 0;

    public ConstantExpression(){}

    public ConstantExpression(Object value){
        this(value, 0);
    }

    public ConstantExpression(Object value, int lineNumber) {
        this.value = value;
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

    @Override
    public int hashCode() {
        return this.getClass().getName().hashCode() + this.value.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        ConstantExpression that = (ConstantExpression) o;
        return this.value == null ? that.value == null : this.value.equals(that.value);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        return this;
    }
}
