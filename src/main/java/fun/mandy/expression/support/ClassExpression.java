package fun.mandy.expression.support;

import fun.mandy.expression.Expression;

public class ClassExpression implements Expression {
    private Class<?> value;

    public ClassExpression(){}

    public ClassExpression(Class<?> value){
        this.value = value;
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
}
