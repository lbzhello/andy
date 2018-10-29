package xyz.lbzh.andy.tokenizer.support;

import xyz.lbzh.andy.core.Definition;
import xyz.lbzh.andy.expression.ExpressionFactory;
import xyz.lbzh.andy.expression.ast.StringExpression;
import xyz.lbzh.andy.expression.ast.TokenExpression;
import xyz.lbzh.andy.tokenizer.Token;
import xyz.lbzh.andy.tokenizer.Tokenizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.HashSet;
import java.util.Set;

public class TemplateTokenizer implements Tokenizer<Token> {
    private Reader reader;
    private int currentChar = ' ';
    private Token currentToken = Definition.HOF;

    private static final Set<Character> delimiter = new HashSet<>();
    static {
        delimiter.add('`');
        delimiter.add('(');
        delimiter.add(')');
        delimiter.add('\n');
    }

    @Override
    public void init(Reader reader) {
        try {
            this.reader = reader;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Token getToken() {
        return currentToken;
    }

    @Override
    public Reader getReader() {
        return this.reader;
    }

    @Override
    public void close() throws IOException {
        this.reader.close();
    }

    @Override
    public boolean hasNext() {
        return this.currentChar != -1;
    }

    @Override
    public Token next() {
        if (this.isDelimiter(getChar())) {
            char delimiter = getChar();
            nextChar(); //eat
            currentToken = ExpressionFactory.string(String.valueOf(delimiter), getLineNumber());
            return currentToken;
        } else {
            currentToken = nextFragment();
            return currentToken;
        }
    }

    private StringExpression nextFragment() {
        StringBuffer sb = new StringBuffer();
        //e.g. <name date=(var 1)>liu</name> => < | name date= | (var 1) | > | liu | < | / | name | >
        while (hasNext() && !this.isDelimiter(getChar()) && getChar() != '\uFFFF') {
            sb.append(getChar());
            nextChar();
        }
        return ExpressionFactory.string(sb.toString(), getLineNumber());
    }

    private char nextChar() {
        try {
            currentChar = reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (char) currentChar;
    }

    private char getChar() {
        return (char) currentChar;
    }

    private boolean isDelimiter(Character c) {
        return delimiter.contains(c);
    }
}
