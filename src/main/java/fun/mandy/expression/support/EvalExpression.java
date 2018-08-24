package fun.mandy.expression.support;

import fun.mandy.expression.Expression;

import java.util.List;

public class EvalExpression extends ListExpression {
    private Expression head;
    
    public EvalExpression(Expression head, Expression... list){
        super(list);
        this.head = head;
    }

    public EvalExpression(Expression head, List<Expression> list) {
        super(list);
        this.head = head;
    }
    
    public EvalExpression(String head, Expression... list) {
        this(new SymbolExpression(head), list);
    }
    

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (Expression expression : this.getList()) {
            sb.append(" " + expression);
        }
        return "(" + this.head.toString() + sb.toString() + ")";
    }

    public Expression head() {
        return head;
    }


}
