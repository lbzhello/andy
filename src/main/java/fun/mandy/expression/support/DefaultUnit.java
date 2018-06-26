package fun.mandy.expression.support;

import fun.mandy.expression.*;

import java.util.*;

public class DefaultUnit implements Unit<Name, Expression> {
    //父节点
    private transient Context<Name,Expression> parent = null;
    //参数
    private Map<Name,Expression> parameter = new HashMap<>();
    //子节点
    private Map<Name,Expression> child = new HashMap<>();
    //计算流
    private List<Expression> expressionStream = new LinkedList<>();

    public DefaultUnit() {}

    public DefaultUnit(Context<Name, Expression> parent) {
        this.parent = parent;
    }
    
    public DefaultUnit(Builder builder) {
        this.parent = builder.parent;
        this.parameter = builder.parameter;
        this.child = builder.child;
        this.expressionStream = builder.expressionStream;
    }

    public static final class Builder {
        private Context<Name,Expression> parent;
        private Map<Name,Expression> parameter;
        private Map<Name,Expression> child;
        private List<Expression> expressionStream;
        
        public DefaultUnit build() {
            return new DefaultUnit(this);
        }
        
        public Builder parent(Context<Name,Expression> parent) {
            this.parent = parent;
            return this;
        }
        
        public Builder parameter(Map<Name,Expression> parameter){
            this.parameter = parameter;
            return this;
        }
        
        public Builder child(Map<Name,Expression> child){
            this.child = child;
            return this;
        }

        public Builder expressionStream(List<Expression> expressionStream) {
            this.expressionStream = expressionStream;
            return this;
        }
    }
    

    @Override
    public Expression lookup(Name key) {
        return null;
    }

    @Override
    public Expression bind(Name key, Expression value) {
        return null;
    }

    @Override
    public Context<Name, Expression> getParent() {
        return null;
    }

    @Override
    public void setParent(Context<Name, Expression> unit) {
        this.parent = unit;
    }

    @Override
    public void setChild(Name name, Expression expression) {
        this.child.put(name, expression);
    }

    @Override
    public void addEvalStream(Expression expression) {
        this.expressionStream.add(expression);
    }

    @Override
    public void setParameter(Map<Name, Expression> parameter) {
        this.parameter = parameter;
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        return null;
    }

    @Override
    public String toString() {
        String param = "(" + this.parameter.keySet().toString() + ")";
        String props = this.child.toString();
        StringBuffer evalStream = new StringBuffer();
        for (Expression tmp : this.expressionStream) {
            evalStream.append(tmp.toString());
        }
        return param + "{" + props + evalStream + "}";
    }
}
