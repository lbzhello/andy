package fun.mandy.tokenizer.support;

import fun.mandy.constant.Constants;
import fun.mandy.exception.Exceptions;
import fun.mandy.tokenizer.Token;
import fun.mandy.tokenizer.Tokenizer;
import fun.mandy.util.Application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;

public class DefaultTokenizer implements Tokenizer<Token<Integer,String>> {
    private LineNumberReader lineNumberReader;
    private Token<Integer,String> token;
    private int currentChar = ' ';

    /**
     * 提供给java config用于配置类
     */
    public static final class Builder {
        private Token<Integer,String> token;

        public Builder(){}

        public Tokenizer<Token<Integer,String>> build(){
            return new DefaultTokenizer(this);
        }

        public Builder token(Token<Integer,String> token){
            this.token = token;
            return this;
        }
    }

    //提供默认构造函数
    public DefaultTokenizer(){}

    //只能通过Builder调用
    private DefaultTokenizer(Builder builder){
        this.token = builder.token;
    }

    @Override
    public void init(Reader reader) {
        this.lineNumberReader = new LineNumberReader(new BufferedReader(reader));
    }

    @Override
    public void close(){
        try {
            lineNumberReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Token<Integer,String> nextToken(){
        try {
            StringBuffer sb = new StringBuffer();
            while (!isEOF()) {
                if (!Character.isWhitespace(getChar())) { //不是空白字符
                   if(getChar() == '"'){ //String
                       return nextString();
                   } else if (Application.isDelimiter(getChar())) { //间隔符直接返回
                       nextChar(); //eat
                       return new DefaultToken((int)getChar(), String.valueOf(getChar()));
                   } else if (Character.isLetter(getChar())) {
                       nextChar(); //eat
                   }
                } else { //空白字符
                    System.out.println("yes");
                    nextChar(); //eat
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Tokenizer Running...");
        return null;
    }

    @Override
    public boolean hasNextToken(){
        return !isEOF();
    }

    private boolean isEOF() {
        return currentChar == -1;
    }

    private char nextChar() throws IOException {
        currentChar = lineNumberReader.read();
        System.out.println((char)currentChar);
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
    private Token<Integer, String> nextString() throws IOException {
        StringBuffer sb = new StringBuffer();
        nextChar(); //eat '"'
        while (getChar() != '"') {
            sb.append(getChar());
            nextChar();
        }
        nextChar(); //eat '"'
        return new DefaultToken(Constants.STRING, sb.toString());
    }

    /**
     * e.g. 1234 12.23
     * @return
     * @throws IOException
     */
    private Token<Integer, String> nextNumber() throws IOException, Exceptions.NumberFormatException {
        StringBuffer sb = new StringBuffer();
        while (Character.isDigit(getChar()) || getChar() == '.') {
            sb.append(getChar());
            nextChar();
        }
        if (!Character.isWhitespace(getChar())) { //e.g. 123.45abc
            throw new Exceptions.NumberFormatException("Exceptions Number Format!");
        }
        return new DefaultToken(Constants.NUMBER, sb.toString());
    }

    /**
     * e.g. name2
     * @return
     * @throws IOException
     */
    private Token<Integer, String> nextSymbol() throws IOException {
        StringBuffer sb = new StringBuffer();
        while (Character.isDigit(getChar()) || Character.isAlphabetic(getChar())) {
            sb.append(getChar());
            boolean b = Character.isSpaceChar(' ');
            boolean b2 = Character.isWhitespace(' ');
            boolean b3 = Character.isLetter(5);
            nextChar();
        }
        return new DefaultToken(Constants.SYMBOL, sb.toString());
    }

}
