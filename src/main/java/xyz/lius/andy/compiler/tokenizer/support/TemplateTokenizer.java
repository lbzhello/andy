package xyz.lius.andy.compiler.tokenizer.support;

import xyz.lius.andy.core.Definition;
import xyz.lius.andy.expression.ExpressionFactory;
import xyz.lius.andy.expression.ast.StringExpression;
import xyz.lius.andy.io.CharIterator;
import xyz.lius.andy.compiler.tokenizer.Token;
import xyz.lius.andy.compiler.tokenizer.Tokenizer;

import java.util.HashSet;
import java.util.Set;

public class TemplateTokenizer implements Tokenizer<Token> {
    private CharIterator iterator;
    private Token currentToken = Definition.HOF;

    private static final Set<Character> delimiter = new HashSet<>();
    static {
        delimiter.add('`');
        delimiter.add('(');
        delimiter.add(')');
        delimiter.add('\n');
    }

    @Override
    public void setResource(CharIterator iterator) {
        this.iterator = iterator;
    }

    @Override
    public Token current() {
        return currentToken;
    }

    @Override
    public boolean hasNext() {
        return this.iterator.current() != CharIterator.DONE;
    }

    @Override
    public Token next() {
        if (this.isDelimiter(iterator.current())) {
            char delimiter = iterator.current();
            iterator.next(); //eat
            currentToken = ExpressionFactory.string(String.valueOf(delimiter), getLineNumber());
            return currentToken;
        } else {
            currentToken = nextFragment();
            return currentToken;
        }
    }

    private StringExpression nextFragment() {
        StringBuffer sb = new StringBuffer();
        while (hasNext() && !this.isDelimiter(iterator.current()) && iterator.current() != '\uFFFF') {
            sb.append(iterator.current());
            iterator.next();
        }
        return ExpressionFactory.string(sb.toString(), getLineNumber());
    }

    private boolean isDelimiter(Character c) {
        return delimiter.contains(c);
    }
}
