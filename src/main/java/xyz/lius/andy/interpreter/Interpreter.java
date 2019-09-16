package xyz.lius.andy.interpreter;

import xyz.lius.andy.expression.Complex;
import xyz.lius.andy.expression.Expression;
import xyz.lius.andy.expression.StackFrame;
import xyz.lius.andy.interpreter.parser.Parser;
import xyz.lius.andy.interpreter.parser.support.DefaultParser;
import xyz.lius.andy.io.support.FileCharIterator;

/**
 * 解释器
 */
public class Interpreter {

    private final ScriptLoader scriptLoader;

    public Interpreter(ScriptLoader scriptLoader) {
        this.scriptLoader = scriptLoader;
    }

    public Expression interpret(String name) {
        Complex complex = null;
        try {
            complex = scriptLoader.loadScript(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new StackFrame(complex).run();
    }

}
