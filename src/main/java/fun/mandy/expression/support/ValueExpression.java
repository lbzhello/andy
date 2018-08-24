package fun.mandy.expression.support;

import fun.mandy.expression.Expression;

public class ValueExpression implements Expression {
    protected Object value;

    public ValueExpression(){}

    public ValueExpression(Object value){
        this.value = value;
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
