package xyz.lius.andy.interpreter;

import xyz.lius.andy.core.Definition;
import xyz.lius.andy.expression.Complex;
import xyz.lius.andy.expression.Expression;
import xyz.lius.andy.expression.ExpressionFactory;
import xyz.lius.andy.expression.ast.CurlyBracketExpression;
import xyz.lius.andy.expression.context.ExpressionContext;
import xyz.lius.andy.interpreter.parser.Parser;
import xyz.lius.andy.interpreter.parser.support.DefaultParser;
import xyz.lius.andy.io.support.FileCharIterator;

import java.io.IOException;

public class FileScriptLoader implements ScriptLoader {
    private FileCharIterator iterator;
    private Parser<Expression> parser;

    public FileScriptLoader() {
        this.iterator = new FileCharIterator();
        this.parser = new DefaultParser(iterator);
    }

    @Override
    public Complex loadScript(String path) throws IOException {
        iterator.setSource(path);
        iterator.refresh();
        CurlyBracketExpression curlyBracketExpression = ExpressionFactory.curlyBracket();
        try {
            while (parser.hasNext()) {
                curlyBracketExpression.add(parser.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ExpressionContext context = new ExpressionContext();
        context.add(Definition.FILE_DIRECTORY, ExpressionFactory.symbol(iterator.getFile().getParentFile().getCanonicalPath()));
        return curlyBracketExpression.eval(context);
    }
}
