package xyz.lius.andy.interpreter;

import xyz.lius.andy.core.Definition;
import xyz.lius.andy.interpreter.Interpreter;
import xyz.lius.andy.interpreter.parser.Parser;
import xyz.lius.andy.expression.*;
import xyz.lius.andy.expression.context.ExpressionContext;

import java.io.File;
import java.io.IOException;

/**
 * 表达式求值引擎
 */
public class ReplEngine {
    private final Context<Name, Expression> context = new ExpressionContext();
    private final Parser<Expression> parser = Interpreter.getDefaultParser();

    public Expression eval(String expression) {
        return this.eval(parser.parseString(expression));
    }

    public Expression evalFile(String fileName) {
        ScriptSession session = new FileScriptSession();
        session.setSource(fileName);
        session.refresh();
        Complex complex = null;
        try {
            complex = session.parse();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Expression rst = new StackFrame(complex).run();

        System.out.println("RST: ");
        ExpressionUtils.print(rst);
        return rst;

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
                context.add(Definition.FILE_DIRECTORY, ExpressionFactory.symbol(parent.getCanonicalPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Expression rst;
        if (TypeCheck.isCurlyBracket(expression)) {
            Complex complex = (Complex) expression.eval(context); //setParameters and generate a runtime expression
            rst = new StackFrame(complex).run();
        } else {
            rst = expression.eval(context);
        }

        System.out.print("RST: ");
        ExpressionUtils.print(rst);
        return rst;
    }
}
