package xyz.lius.andy.expression;

import java.util.List;

public class StackFrame extends AbstractContext<Name, Expression> implements Expression {

    private List<Expression> codes;
    private Context<Name, Expression> localVariableTable;
    private Context<Name, Expression> contantPool;

    @Override
    public Expression eval(Context<Name, Expression> context) {
        return null;
    }

    @Override
    public Expression lookup(Name key) {
        Expression o = super.lookup(key);
        return o == null ? ExpressionType.NIL : o;
    }

}
