package xyz.lbzh.andy.expression.ast;

import org.springframework.objenesis.ObjenesisBase;
import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.Name;
import xyz.lbzh.andy.tokenizer.Token;

public class TokenExpression implements Token, Expression, Name {
    protected Object value;
    protected int lineNumber = -1;

    public TokenExpression(){}

    public TokenExpression(Object value){
        this(value, -1);
    }

    public TokenExpression(Object value, int lineNumber) {
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
        TokenExpression that = (TokenExpression) o;
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
}
