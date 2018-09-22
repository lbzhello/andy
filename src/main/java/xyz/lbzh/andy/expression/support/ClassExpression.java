package xyz.lbzh.andy.expression.support;

import xyz.lbzh.andy.expression.Expression;

public class ClassExpression implements Expression {
    private Class<?> value;

    public ClassExpression(Class<?> value){
        this.value = value;
    }

    public Class<?> classOf() {
        return value;
    }

    @Override
    public int hashCode() {
        return value.getName().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(o == null || this.getClass() != o.getClass()) return false;
        ClassExpression that = (ClassExpression)o;
        return this.value == null ? that.value == null : this.value.equals(that.value);
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}
