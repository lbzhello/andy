package xyz.lbzh.andy.tokenizer.support;

import xyz.lbzh.andy.core.Definition;
import xyz.lbzh.andy.expression.ExpressionFactory;
import xyz.lbzh.andy.expression.ast.StringExpression;
import xyz.lbzh.andy.io.CharIter;
import xyz.lbzh.andy.tokenizer.Token;
import xyz.lbzh.andy.tokenizer.Tokenizer;

import java.util.HashSet;
import java.util.Set;

public class StringTokenizer implements Tokenizer<Token> {
    private CharIter iterator;
    private Token currentToken = Definition.HOF;

    private static final Set<Character> delimiter = new HashSet<>();
    static {
        delimiter.add('"');
        delimiter.add('(');
        delimiter.add(')');
    }

    @Override
    public void init(CharIter iterator) {
        this.iterator = iterator;
    }

    @Override
    public Token current() {
        return currentToken;
    }

    @Override
    public boolean hasNext() {
        return this.iterator.current() != CharIter.DONE;
    }

    @Override
    public Token next() {
        if (this.isDelimiter(iterator.current())) {
            char delimiter = iterator.current();
            iterator.next(); //eat
            currentToken = ExpressionFactory.symbol(String.valueOf(delimiter), getLineNumber());
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
