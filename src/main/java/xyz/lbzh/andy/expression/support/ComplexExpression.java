package xyz.lbzh.andy.expression.support;

import xyz.lbzh.andy.expression.Context;
import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.Name;
import xyz.lbzh.andy.expression.NameEnum;
import xyz.lbzh.andy.util.Builder;

import java.util.Collections;
import java.util.List;

public class ComplexExpression implements Expression {
    private List<Expression> parameters;
    private List<Expression> list;

    private Context<Name, Object> context;

    public ComplexExpression(Context<Name, Object> context) {
        this.context = context;
    }

    public ComplexExpression parameters(List<Expression> parameters) {
        this.parameters = parameters;
        // param1 -> NameEnum.$0; param2 -> NameEnum.$1; ...
        for (int i = 0; i < this.parameters.size(); i++) {
            context.bind(this.parameters.get(i).getName(), NameEnum.values()[i]);
        }
        return this;
    }

    public ComplexExpression list(List<Expression> list) {
        this.list = list;
        return this;
    }

//    private ComplexExpression(ComplexExpressionBuilder builder) {
//        this.parameters = builder.parameters;
//        this.list = builder.list;
//    }
//
//    public ComplexExpressionBuilder builder() {
//        return new ComplexExpressionBuilder();
//    }
//
//    public static class ComplexExpressionBuilder implements Builder<ComplexExpression> {
//        private List<Expression> parameters;
//        private List<Expression> list;
//
//        public ComplexExpressionBuilder parameters(List<Expression> parameters) {
//            this.parameters = parameters;
//            return this;
//        }
//
//        public ComplexExpressionBuilder list(List list) {
//            this.list = list;
//            return this;
//        }
//
//        public ComplexExpression build() {
//            return new ComplexExpression(this);
//        }
//    }

    /**
     * e.g. parameters{...}
     * @param parameters
     * @param list
     * @return
     */
    public ComplexExpression build(List<Expression> parameters, List<Expression> list) {
        this.parameters = parameters;
        this.list = list;
        init();
        return this;
    }

    private void init() {
        List<Expression> list = Collections.emptyList();
        if (parameters instanceof RoundBracketExpression) { //e.g. (a b c){...}
            list = ((RoundBracketExpression) parameters).tail();
        } else if (parameters instanceof SquareBracketExpression) { //e.g. (a, b, c){...}
            list = ((SquareBracketExpression) parameters).list();
        } else {

        }
        // param1 -> NameEnum.$0; param2 -> NameEnum.$1; ...
        for (int i = 0; i < list.size(); i++) {
            context.bind(list.get(i).getName(), NameEnum.values()[i]);
        }
    }

}
