package xyz.lbzh.andy.expression;

import xyz.lbzh.andy.core.Definition;
import xyz.lbzh.andy.core.ApplicationFactory;
import xyz.lbzh.andy.parser.Parser;

public class ReplEngine {
    private final Context<Name, Expression> context = Definition.getCoreContext();
    private final Parser<Expression> parser = ApplicationFactory.get(Parser.class);

    public Expression eval(String expression) {
        return this.eval(parser.parseString(expression));
    }

    public Expression evalFile(String fileName) {
        return this.eval(parser.parseFile(fileName));
    }

    public Expression eval(Expression expression) {
        System.out.println("AST: " + expression);
        Expression rst;
        if (ExpressionUtils.isCurlyBracket(expression)) {
            Expression complex = expression.eval(context); //parameters and generate a runtime expression
            rst = complex.eval(new ExpressionContext());
        } else {
            rst = expression.eval(context);
        }

        if (ExpressionUtils.hasError(rst)) {
            System.err.println(rst);
        } else {
            System.out.println(rst);
        }
        return rst;
    }
}
