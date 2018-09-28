package xyz.lbzh.andy.expression.support;

import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.Name;

public class ValueExpression implements Expression, Name {
    protected Object value;

    public ValueExpression(){}

    public ValueExpression(Object value){
        this.value = value;
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
        ValueExpression that = (ValueExpression) o;
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
