package fun.mandy.parser.support;

import fun.mandy.constant.Constants;
import fun.mandy.expression.Expression;
import fun.mandy.parser.Parser;
import fun.mandy.tokenizer.Token;
import fun.mandy.tokenizer.Tokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Reader;

@Component
public class DefaultParser implements Parser<Expression> {
    @Autowired
    private Tokenizer<Token> tokenizer;

    @Override
    public Expression generate() {
        while (tokenizer.hasNextToken()) {
            Token<Integer,String> token = tokenizer.nextToken();
            if(token.type() == Constants.SYMBOL){

            }

        }
        System.out.println("i am Parser");

        return null;
    }

    @Override
    public void init(Reader reader) {
        tokenizer.init(reader);
    }

    @Override
    public void close() {
        tokenizer.close();
    }
}
