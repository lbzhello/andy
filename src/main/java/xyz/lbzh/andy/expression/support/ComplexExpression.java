package xyz.lbzh.andy.expression.support;

import xyz.lbzh.andy.expression.Context;
import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.Name;
import xyz.lbzh.andy.expression.NameEnum;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ComplexExpression implements Expression {
    private Expression expression;
    private CurlyBracketExpression curlyBracketExpression;

    private Context<Name, Object> context;

    public ComplexExpression(Context<Name, Object> context) {
        this.context = context;
    }

    /**
     * e.g. expression{...}
     * @param expression
     * @param curlyBracketExpression
     * @return
     */
    public ComplexExpression build(Expression expression, CurlyBracketExpression curlyBracketExpression) {
        this.expression = expression;
        this.curlyBracketExpression = curlyBracketExpression;
        init();
        return this;
    }

    private void init() {
        List<Expression> list = Collections.emptyList();
        if (expression instanceof RoundBracketExpression) { //e.g. (a b c){...}
            list = ((RoundBracketExpression) expression).tail();
        } else if (expression instanceof SquareBracketExpression) { //e.g. (a, b, c){...}
            list = ((SquareBracketExpression) expression).list();
        } else {

        }
        // param1 -> NameEnum.$0; param2 -> NameEnum.$1; ...
        for (int i = 0; i < list.size(); i++) {
            context.bind(list.get(i).getName(), NameEnum.values()[i]);
        }
    }

}
