package xyz.lius.andy.compiler;

import xyz.lius.andy.expression.Expression;
import xyz.lius.andy.compiler.parser.Parser;
import xyz.lius.andy.compiler.parser.support.DefaultParser;
import xyz.lius.andy.compiler.tokenizer.Token;
import xyz.lius.andy.compiler.tokenizer.Tokenizer;
import xyz.lius.andy.compiler.tokenizer.support.FileTokenizer;

/**
 * 原型工厂类
 */
public class Compiler {
    
    public static Tokenizer tokenizer(){
        Tokenizer<Token> tokenizer = new FileTokenizer();
        return tokenizer;
    }

    
    
    public static Parser parser(){
        Parser<Expression> parser = new DefaultParser(tokenizer());
        return parser;
    }

}
