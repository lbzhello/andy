package xyz.lbzh.andy.tokenizer.support;

import xyz.lbzh.andy.expression.ExpressionFactory;
import xyz.lbzh.andy.expression.ast.StringExpression;
import xyz.lbzh.andy.tokenizer.Token;
import xyz.lbzh.andy.tokenizer.Tokenizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.HashSet;
import java.util.Set;

public class StringTokenizer implements Tokenizer<Token> {
    private LineNumberReader lineNumberReader;
    private int currentChar = ' ';

    private static final Set<Character> delimiter = new HashSet<>();
    static {
        delimiter.add('"');
        delimiter.add('(');
        delimiter.add(')');
    }

    @Override
    public void init(Reader reader) {
        try {
            this.lineNumberReader = new LineNumberReader(new BufferedReader(reader));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Reader getReader() {
        return this.lineNumberReader;
    }

    @Override
    public void close() throws IOException {
        this.lineNumberReader.close();
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
            return ExpressionFactory.symbol(String.valueOf(delimiter), getLineNumber());
        } else {
            return nextFragment();
        }
    }

    private StringExpression nextFragment() {
        StringBuffer sb = new StringBuffer();
        while (hasNext() && !this.isDelimiter(getChar()) && getChar() != '\uFFFF') {
            sb.append(getChar());
            nextChar();
        }
        return ExpressionFactory.string(sb.toString(), getLineNumber());
    }

    private char nextChar() {
        try {
            currentChar = lineNumberReader.read();
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
