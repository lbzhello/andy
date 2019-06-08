package xyz.lius.andy.compiler.tokenizer.support;

import xyz.lius.andy.core.Definition;
import xyz.lius.andy.expression.ExpressionFactory;
import xyz.lius.andy.io.CharIterator;
import xyz.lius.andy.compiler.tokenizer.LineNumberToken;
import xyz.lius.andy.compiler.tokenizer.Token;
import xyz.lius.andy.compiler.tokenizer.TokenFlag;
import xyz.lius.andy.compiler.tokenizer.Tokenizer;

import java.io.*;

public class FileTokenizer implements Tokenizer<Token> {
    private CharIterator iterator;
    private int lineNumber = 1;
    private Token currentToken = Definition.HOF;

    @Override
    public void setResource(CharIterator iterator) {
        this.iterator = iterator;
    }

    @Override
    public Token current() {
        return currentToken;
    }

    @Override
    public int getLineNumber() {
        return this.lineNumber; //1 based
    }

    @Override
    public Token next(){
        try {
            while (hasNext()) {
                if (!Character.isWhitespace(iterator.current())) { //不是空白字符
                   if(iterator.current() == '"'){ //String
                       currentToken = nextString();
                       return currentToken;
                   } else if (Definition.isDelimiter(iterator.current())) { //间隔符直接返回
                       currentToken = Definition.getDelimiter(iterator.current());
                       iterator.next(); //eat
                       return currentToken;
                   } else if (Character.isDigit(iterator.current())) { //number
                       currentToken = nextNumber();
                       return currentToken;
                   } else if (iterator.current() == '`') {
//                       iterator.next(); //eat '`'
                       return TokenFlag.BACK_QUOTE;
                   } else if (iterator.current() == '/') {
                       iterator.next();  //eat '/'
                       if (iterator.current() == '/') { //注释
                           iterator.next(); //eat '/'
                           while (iterator.current() != '\n' && iterator.hasNext()) {
                               iterator.next();
                           }
                       } else {
                           return ExpressionFactory.symbol("/");
                       }
                   } else {
                       currentToken = nextSymbol();
                       return currentToken;
                   }
                } else { //空白字符
                    while (Character.isWhitespace(iterator.current())) {
                        if (iterator.current() == '\n') lineNumber++;
                        iterator.next(); //eat
                    }
                    if (iterator.current() == '(') {  //e.g. name {...
                        iterator.next();
                        currentToken = TokenFlag.ROUND_BRACKET_FREE;
                        return currentToken;
                    } else if (iterator.current() == '[') { //e.g. name [...
                        iterator.next();
                        currentToken = TokenFlag.SQUARE_BRACKET_FREE;
                        return currentToken;
                    } else if (iterator.current() == '{') { //e.g. name {...
                        iterator.next();
                        currentToken = TokenFlag.CURLY_BRACKET_FREE;
                        return currentToken;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Definition.EOF;
    }

    @Override
    public boolean hasNext(){
        return iterator.current() != CharIterator.DONE;
    }

    /**
     * e.g. "some string"
     * @return
     * @throws IOException
     */
    private LineNumberToken nextString() {
        StringBuffer sb = new StringBuffer();
        iterator.next(); //eat '"'
        while (iterator.current() != '"') {
            sb.append(iterator.current());
            iterator.next();
        }
        iterator.next(); //eat '"'
        return ExpressionFactory.string(sb.toString(), getLineNumber());
    }

    /**
     * e.g. 1234 12.23
     * @return
     * @throws IOException
     */
    private LineNumberToken nextNumber() {
        StringBuffer sb = new StringBuffer();
        while (iterator.current() == '.' || !Character.isWhitespace(iterator.current()) && !Definition.isDelimiter(iterator.current()) && iterator.current() != '\uFFFF') {
            sb.append(iterator.current());
            iterator.next();
        }
        return ExpressionFactory.number(sb.toString(), getLineNumber());
    }

    /**
     * e.g. name2, na-rt, sym+nnn...
     * @return
     * @throws IOException
     */
    private Token nextSymbol() {
        StringBuffer sb = new StringBuffer();
        if (iterator.current() == '<') { //judgement if it's template
            sb.append(iterator.current());
            iterator.next(); //eat '<'
            if (Character.isWhitespace(iterator.current()) || iterator.current() == '=') {
                sb.append(iterator.current());
                iterator.next(); //eat
                return ExpressionFactory.symbol(sb.toString().trim(), getLineNumber());
            } else {
                iterator.previous(); //back to '<'
                return TokenFlag.ANGLE_BRACKET;
            }
        } else {
            while (!Character.isWhitespace(iterator.current()) && !Definition.isDelimiter(iterator.current()) && iterator.current() != '\uFFFF') {
                sb.append(iterator.current());
                iterator.next();
            }
            return ExpressionFactory.symbol(sb.toString(), getLineNumber());
        }
    }

}
