package xyz.lius.andy.compiler.tokenizer.support;

import xyz.lius.andy.compiler.tokenizer.LineNumberToken;
import xyz.lius.andy.compiler.tokenizer.Token;
import xyz.lius.andy.compiler.tokenizer.TokenFlag;
import xyz.lius.andy.compiler.tokenizer.Tokenizer;
import xyz.lius.andy.core.Definition;
import xyz.lius.andy.expression.ExpressionFactory;
import xyz.lius.andy.io.CharIterator;

import java.io.IOException;

public class FileTokenizer implements Tokenizer<Token> {
    private CharIterator iterator;
    private int lineNumber;
    private Token currentToken;

    @Override
    public void setResource(CharIterator iterator) {
        this.iterator = iterator;
        lineNumber = 1;
        currentToken = Definition.HOF;
    }

    @Override
    public void reset() {
        currentToken = Definition.HOF;
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
                   } else if (iterator.current() == '`') { //交给外部解析器解析
                       if (currentToken != TokenFlag.BACK_QUOTE) { //初次进入
                           currentToken = TokenFlag.BACK_QUOTE;
                       } else { // '`'已经被消费
                           iterator.next(); //eat '`'
                           currentToken = Definition.HOF;
                       }
                       return currentToken;
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
        return iterator.hasNext();
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
            } else { //xml 交给外部处理
                iterator.previous(); //back to '<'
                if (currentToken != TokenFlag.ANGLE_BRACKET) { //初次进入
                    return TokenFlag.ANGLE_BRACKET;
                } else { //已经处理
                    return TokenFlag.HOF;
                }
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
