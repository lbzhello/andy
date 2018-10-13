package xyz.lbzh.andy.tokenizer.support;

import xyz.lbzh.andy.core.Definition;
import xyz.lbzh.andy.exception.Exceptions;
import xyz.lbzh.andy.expression.ExpressionFactory;
import xyz.lbzh.andy.expression.support.*;
import xyz.lbzh.andy.tokenizer.Token;
import xyz.lbzh.andy.tokenizer.Tokenizer;

import java.io.*;
import java.math.BigDecimal;

public class DefaultTokenizer implements Tokenizer<Token> {
    private LineNumberReader lineNumberReader;
    private Token token;
    private int currentChar = ' ';

    /**
     * 提供给java config用于配置类
     */
    public static final class Builder {
        private Token token;

        public Builder(){}

        public Tokenizer<Token> build(){
            return new DefaultTokenizer(this);
        }

        public Builder token(Token token){
            this.token = token;
            return this;
        }
    }

    /**
     * 提供默认构造函数
     * 初始化资源
     */
    public DefaultTokenizer(){
//        this.lineNumberReader = new LineNumberReader(new BufferedReader(NameExpression.getReader()));
    }

    //只能通过Builder调用
    private DefaultTokenizer(Builder builder){
        this.token = builder.token;
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
    public void close() throws IOException {
        lineNumberReader.close();
    }

    @Override
    public Token next(){
        try {
            StringBuffer sb = new StringBuffer();
            while (!isEOF()) {
                if (!Character.isWhitespace(getChar())) { //不是空白字符
                   if(getChar() == '"'){ //String
                       return nextString();
                   } else if (Definition.isDelimiter(getChar())) { //间隔符直接返回
                       Token token = ExpressionFactory.token(String.valueOf(getChar()));
                       nextChar(); //eat
                       return token;
                   } else if (Character.isDigit(getChar())) { //number
                       return nextNumber();
                   } else {
                       return nextSymbol();
                   }
                } else { //空白字符
                    while (Character.isWhitespace(getChar())) {
                        nextChar(); //eat
                    }
                    if (getChar() == '(') {  //e.g. name {...
                        Token token = ExpressionFactory.token(Definition.SPACE + String.valueOf(getChar()));
                        nextChar();
                        return token;
                    } else if (getChar() == '[') { //e.g. name [...
                        Token token = ExpressionFactory.token(Definition.SPACE + String.valueOf(getChar()));
                        nextChar();
                        return token;
                    } else if (getChar() == '{') { //e.g. name {...
                        Token token = ExpressionFactory.token(Definition.SPACE + String.valueOf(getChar()));
                        nextChar();
                        return  token;
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
        return !isEOF();
    }

    private boolean isEOF() {
        return currentChar == -1;
    }

    private char nextChar() throws IOException {
        currentChar = lineNumberReader.read();
        return (char)currentChar;
    }

    private char getChar() {
        return (char)currentChar;
    }

    /**
     * e.g. "some string"
     * @return
     * @throws IOException
     */
    private Token nextString() throws IOException {
        StringBuffer sb = new StringBuffer();
        nextChar(); //eat '"'
        while (getChar() != '"') {
            sb.append(getChar());
            nextChar();
        }
        nextChar(); //eat '"'
        return ExpressionFactory.string(sb.toString());
    }

    /**
     * e.g. 1234 12.23
     * @return
     * @throws IOException
     */
    private Token nextNumber() throws IOException, Exceptions.NumberFormatException {
        StringBuffer sb = new StringBuffer();
        while (Character.isDigit(getChar()) || getChar() == '.') {
            sb.append(getChar());
            nextChar();
        }
        if (Character.isWhitespace(getChar()) || Definition.isDelimiter(getChar()) || isEOF()) {
            return ExpressionFactory.number(sb.toString());
        } else {
            throw new Exceptions.NumberFormatException("Exceptions Number Format!");
        }

//        if (!Character.isWhitespace(getChar()) && !NameExpression.isDelimiter(getChar())) { //e.g. 123.45abc
//            throw new Exceptions.NumberFormatException("Exceptions Number Format!");
//        }
//        return new NumberExpression(sb.toString());
    }

    /**
     * e.g. name2, na-rt, sym+nnn...
     * @return
     * @throws IOException
     */
    private Token nextSymbol() throws IOException {
        StringBuffer sb = new StringBuffer();
        while (!Character.isWhitespace(getChar()) && !Definition.isDelimiter(getChar()) && getChar() != '\uFFFF') {
            sb.append(getChar());
            nextChar();
        }
        return ExpressionFactory.symbol(sb.toString());
    }

}
