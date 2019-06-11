package xyz.lius.andy.core;

import xyz.lius.andy.compiler.Compiler;
import xyz.lius.andy.expression.*;
import xyz.lius.andy.compiler.parser.Parser;

import java.io.File;
import java.io.IOException;

/**
 * 表达式求值引擎
 */
public class ReplEngine {
    private final Context<Name, Expression> context = Definition.getCoreContext();
    private final Parser<Expression> parser = Compiler.parser();

    public Expression eval(String expression) {
        return this.eval(parser.parseString(expression));
    }

    public Expression evalFile(String fileName) {
        return this.eval(parser.parseFile(fileName), fileName);
    }

    public Expression eval(Expression expression) {
        return this.eval(expression, null);
    }

    public Expression eval(Expression expression, String fileName) {
        System.out.println("AST: " + expression);

        if (fileName != null) {
            try {
                //父目录放入上下文，用于脚本文件导入（表示从当前文件夹），详见 ImportExpression
                File parent = new File(fileName).getCanonicalFile().getParentFile();
                context.add(Definition.FILE_PARENT, ExpressionFactory.symbol(parent.getCanonicalPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Expression rst;
        if (ExpressionUtils.isCurlyBracket(expression)) {
            Expression complex = expression.eval(context); //setParameters and generate a runtime expression
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
