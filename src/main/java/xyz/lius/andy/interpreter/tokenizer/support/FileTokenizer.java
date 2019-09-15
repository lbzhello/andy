package xyz.lius.andy.interpreter.tokenizer.support;

import xyz.lius.andy.interpreter.tokenizer.LineNumberToken;
import xyz.lius.andy.interpreter.tokenizer.Token;
import xyz.lius.andy.interpreter.tokenizer.Tokenizer;
import xyz.lius.andy.core.Definition;
import xyz.lius.andy.expression.ExpressionFactory;
import xyz.lius.andy.io.CharIterator;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileTokenizer implements Tokenizer<Token> {
    private CharIterator iterator;
    private int lineNumber;
    private Token currentToken;

    private static final Map<Character, Token> delimiter = new HashMap<>();
    static {
        delimiter.put(',', Token.COMMA);
        delimiter.put(';', Token.SEMICOLON);
        delimiter.put('.', Token.POINT);
        delimiter.put(':', Token.COLON);
        delimiter.put('(', Token.ROUND_BRACKET_LEFT);
        delimiter.put(')', Token.ROUND_BRACKET_RIGHT);
        delimiter.put('{', Token.CURLY_BRACKET_LEFT);
        delimiter.put('}', Token.CURLY_BRACKET_RIGHT);
        delimiter.put('[', Token.SQUARE_BRACKET_LEFT);
        delimiter.put(']', Token.SQUARE_BRACKET_RIGHT);
        delimiter.put('\\',Token.SLASH_LEFT);
        delimiter.put('"', Token.QUOTE_MARK_DOUBLE);
        delimiter.put('\'',Token.QUOTE_MARK_SINGLE);
    }

    private static final boolean isDelimiter(Character c) {
        return delimiter.containsKey(c);
    }

    private static final Token getDelimiter(Character character) {
        return delimiter.get(character);
    }

    public FileTokenizer() {
    }

    public FileTokenizer(CharIterator iterator) {
        setResource(iterator);
    }

    /**
     * 设置数据源，可重用
     * @param iterator
     */
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
                   } else if (isDelimiter(iterator.current())) { //间隔符直接返回
                       currentToken = getDelimiter(iterator.current());
                       iterator.next(); //eat
                       return currentToken;
                   } else if (Character.isDigit(iterator.current())) { //number
                       currentToken = nextNumber();
                       return currentToken;
                   } else if (iterator.current() == '`') { //交给外部解析器解析
                       if (currentToken != Token.BACK_QUOTE) { //初次进入
                           currentToken = Token.BACK_QUOTE;
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
                        if (iterator.current() == '\n') {
                            lineNumber++;
//                            iterator.next();
//                            return currentToken = Token.EOL;
                        }
                        iterator.next(); //eat
                    }

//                    // 返回空格
//                    if (iterator.current() == '('
//                            || iterator.current() == '['
//                            || iterator.current() == '{') {
//                        currentToken = Token.SPACE;
//                        return currentToken;
//
//                    }
                    if (iterator.current() == '(') {
                        iterator.next();
                        return currentToken = Token.ROUND_BRACKET_LEFT_FREE;
                    } else if (iterator.current() == '[') {
                        iterator.next();
                        return currentToken = Token.SQUARE_BRACKET_LEFT_FREE;
                    }else if (iterator.current() == '{') {
                        iterator.next();
                        return currentToken = Token.CURLY_BRACKET_LEFT_FREE;
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currentToken = Definition.EOF;
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
        while (iterator.current() == '.' || !Character.isWhitespace(iterator.current()) && !isDelimiter(iterator.current()) && iterator.current() != '\uFFFF') {
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
        if (iterator.current() == '<') { //judgement if it's xml
            sb.append(iterator.current());
            iterator.next(); //eat '<'
            if (Character.isWhitespace(iterator.current()) || iterator.current() == '=') {
                sb.append(iterator.current());
                iterator.next(); //eat
                return ExpressionFactory.symbol(sb.toString().trim(), getLineNumber());
            } else { //xml 交给外部处理
                iterator.previous(); //back to '<'
                if (currentToken != Token.ANGLE_BRACKET) { //初次进入
                    return Token.ANGLE_BRACKET;
                } else { //已经处理
                    return Definition.HOF;
                }
            }
        } else {
            while (!Character.isWhitespace(iterator.current()) && !isDelimiter(iterator.current()) && iterator.current() != '\uFFFF') {
                sb.append(iterator.current());
                iterator.next();
            }
            return ExpressionFactory.symbol(sb.toString(), getLineNumber());
        }
    }

    private Token nextTmpl() {
        StringBuilder segment = new StringBuilder();
        while (iterator.hasNext()) {
            switch (iterator.current()) {
                //定界符, '\n' 和 '|' 之间的空格忽略
                case '\n':
                    segment.append(iterator.current());
                    iterator.next(); //eat '\n'
                    lineNumber++;
                    int start = segment.length();
                    while (Character.isWhitespace(iterator.current())) {
                        segment.append(iterator.current());
                        iterator.next();
                    }
                    if (iterator.current() == '|') {
                        segment.delete(start, segment.length());
                        iterator.next(); //eat '|'
                    }
                    break;
                case '(':
                    return ExpressionFactory.string(segment.toString(), getLineNumber());
                case '\\':
                    iterator.next(); //eat
                    if (Character.isWhitespace(iterator.current())) {
                        iterator.next(); //skip
                    } else {
                        segment.append(iterator.current()); //add to buffer
                        iterator.next();
                    }
                    break;
                case '`':
                    return ExpressionFactory.string(segment.toString(), getLineNumber());
                default:
                    segment.append(iterator.current());
            }
        }
        throw new RuntimeException("Lack of '`' to end the template!");
    }

    public Token nextXml() {
        StringBuilder segment = new StringBuilder();
        while (iterator.hasNext()) {
            if (iterator.current() == '(' || iterator.current() == '/'
                    || iterator.current() == '<' || iterator.current() == '>') {
                return ExpressionFactory.string(segment.toString(), getLineNumber());
            } else if (iterator.current() == '\n') {
                iterator.next();
                lineNumber++;
            } else {
                segment.append(iterator.current());
            }
        }

        throw new RuntimeException("Lack of '`' to end the template!");
    }

}
