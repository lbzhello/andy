package fun.mandy.expression.support;

import fun.mandy.expression.Expression;

public class ObjectExpression implements Expression {
    protected Object value;

    public ObjectExpression(){}

    public ObjectExpression(Object value){
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
        ObjectExpression that = (ObjectExpression) o;
        return this.value != null ? this.value.equals(that.value) : false;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
