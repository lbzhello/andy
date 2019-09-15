package xyz.lius.andy.interpreter;

import xyz.lius.andy.expression.Expression;
import xyz.lius.andy.interpreter.parser.Parser;
import xyz.lius.andy.interpreter.parser.support.DefaultParser;
import xyz.lius.andy.interpreter.tokenizer.Token;
import xyz.lius.andy.interpreter.tokenizer.Tokenizer;
import xyz.lius.andy.interpreter.tokenizer.support.FileTokenizer;
import xyz.lius.andy.io.support.FileCharIterator;

/**
 * 解释器
 */
public class Interpreter {
    
    public static Parser getDefaultParser(){
        FileCharIterator iterator = new FileCharIterator();
        Tokenizer<Token> tokenizer = new FileTokenizer();
        Parser<Expression> parser = new DefaultParser(tokenizer);
        return parser;
    }

}
