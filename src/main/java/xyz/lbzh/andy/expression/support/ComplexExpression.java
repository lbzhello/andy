package xyz.lbzh.andy.expression.support;

import xyz.lbzh.andy.expression.*;

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

    @Override
    public Expression eval(Context<Name, Object> context) {
        Expression retValue = ExpressionType.NIL;
        for (Expression expression : this.list) {
            retValue = expression.eval(context);
            //return statement
            if (retValue instanceof ReturnExpression) {
                return retValue;
            }
        }
        //or return the last value of the expression
        return retValue;
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

}
