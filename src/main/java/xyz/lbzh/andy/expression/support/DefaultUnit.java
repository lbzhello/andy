package xyz.lbzh.andy.expression.support;

import xyz.lbzh.andy.expression.*;

import java.util.*;

public class DefaultUnit implements Unit<Name, Expression> {
    //父节点
    private transient Context<Name,Expression> parent = null;
    //参数
    private Map<Name,Expression> parameter = new LinkedHashMap<>();
    //子节点
    private Map<Name,Expression> child = new HashMap<>();
    //构造流,构造是运行一次
    private List<Expression> buildStream = new LinkedList<>();
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
        this. buildStream = builder.buildStream;
        this.expressionStream = builder.expressionStream;
    }

    public static final class Builder {
        private Context<Name,Expression> parent;
        private Map<Name,Expression> parameter;
        private Map<Name,Expression> child;
        private List<Expression> buildStream;
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

        public Builder buildStream(List<Expression> buildStream) {
            this.buildStream = buildStream;
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
    public void addBuildStream(Expression expression) {
        this.buildStream.add(expression);
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
        //参数
        String paramStr = this.parameter.keySet().toString();
        paramStr = "(" + paramStr.substring(1,paramStr.length()-1) + ")";
        //子节点/属性
        String childStr = this.child.toString();
        childStr = childStr.substring(1, childStr.length() - 1);

        //计算流
        StringBuffer buildStreamSb = new StringBuffer();
        for (Expression tmp : this.buildStream) {
            buildStreamSb.append(tmp.toString() + " ");
        }

        //计算流
        StringBuffer evalStreamSb = new StringBuffer();
        for (Expression tmp : this.expressionStream) {
            evalStreamSb.append(tmp.toString() + " ");
        }

        return paramStr + "{" + childStr + " " + buildStreamSb + evalStreamSb + "}";
    }
}
