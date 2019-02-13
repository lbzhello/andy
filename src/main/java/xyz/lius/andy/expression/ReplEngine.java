package xyz.lius.andy.expression;

import xyz.lius.andy.core.Definition;
import xyz.lius.andy.core.ApplicationFactory;
import xyz.lius.andy.expression.template.XmlExpression;
import xyz.lius.andy.parser.Parser;

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

        System.out.print("RST: ");
        if (ExpressionUtils.hasError(rst)) {
            System.err.println(rst);
        } else {
            if (ExpressionUtils.isXml(rst)) { //format
                System.out.println(ExpressionUtils.formatXml(ExpressionUtils.asXml(rst)));
            } else {
                System.out.println(rst);
            }
        }
        return rst;
    }
}
