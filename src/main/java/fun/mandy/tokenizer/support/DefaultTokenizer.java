package fun.mandy.tokenizer.support;

import fun.mandy.tokenizer.Token;
import fun.mandy.tokenizer.Tokenizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.stream.Stream;

public class DefaultTokenizer implements Tokenizer<Token<Integer,String>> {
    private LineNumberReader lineNumberReader;
    private Token<Integer,String> token;
    private int currentChar = 0;

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
            while (!isEOF()){
                nextChar();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    private boolean isDelimiter(String sign){
        return false;
    }

}
