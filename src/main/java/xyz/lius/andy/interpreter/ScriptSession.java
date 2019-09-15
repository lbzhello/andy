package xyz.lius.andy.interpreter;

import xyz.lius.andy.expression.Expression;
import xyz.lius.andy.interpreter.parser.Parser;
import xyz.lius.andy.interpreter.parser.support.DefaultParser;
import xyz.lius.andy.interpreter.tokenizer.Token;
import xyz.lius.andy.interpreter.tokenizer.Tokenizer;
import xyz.lius.andy.interpreter.tokenizer.support.FileTokenizer;
import xyz.lius.andy.io.CharIterator;
import xyz.lius.andy.io.support.FileCharIterator;

public class ScriptSession {
    private CharIterator iterator;
    private Tokenizer<Token> tokenizer;
    private Parser<Expression> parser;

    public ScriptSession() {
        this.iterator = new FileCharIterator();
        this.tokenizer = new FileTokenizer();
        this.parser = new DefaultParser(tokenizer);
    }

    public ScriptSession(CharIterator iterator, Tokenizer<Token> tokenizer, Parser<Expression> parser) {
        this.iterator = iterator;
        this.tokenizer = tokenizer;
        this.parser = parser;
    }
}
