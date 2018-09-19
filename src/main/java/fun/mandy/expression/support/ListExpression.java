package fun.mandy.expression.support;

import fun.mandy.expression.Expression;

public class ListExpression extends SExpression {
    public ListExpression(Expression... expressions) {
        super(expressions);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("[");
        if (list != null && list.size() > 0) {
            for (Expression expression : list) {
                sb.append(expression + ",");
            }
            //remove the last comma
            sb.replace(sb.length()-1, sb.length(), "");
        }
        sb.append("]");
        return sb.toString();
    }
}
