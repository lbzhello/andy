package xyz.lius.andy.interpreter;

import xyz.lius.andy.core.Definition;
import xyz.lius.andy.interpreter.parser.Parser;
import xyz.lius.andy.expression.*;
import xyz.lius.andy.expression.context.ExpressionContext;

import java.io.File;
import java.io.IOException;

/**
 * 表达式求值引擎
 */
public class ReplEngine {
    private final ScriptLoader loader = new FileScriptLoader();

    public Expression evalFile(String fileName) throws Exception {
        Complex complex = loader.loadScript(fileName);
        Expression rst = new StackFrame(complex).run();
        System.out.println("RST: ");
        ExpressionUtils.print(rst);
        return rst;

    }

}
