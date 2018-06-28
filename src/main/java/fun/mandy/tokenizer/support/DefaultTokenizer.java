package fun.mandy.tokenizer.support;

import fun.mandy.boot.Application;
import fun.mandy.constant.Constants;
import fun.mandy.constant.ExpressionTypeMode;
import fun.mandy.exception.Exceptions;
import fun.mandy.expression.Expression;
import fun.mandy.expression.support.DelimiterExpression;
import fun.mandy.expression.support.NumberExpression;
import fun.mandy.expression.support.StringExpression;
import fun.mandy.expression.support.SymbolExpression;
import fun.mandy.tokenizer.Token;
import fun.mandy.tokenizer.Tokenizer;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class DefaultTokenizer implements Tokenizer<Expression> {
    private LineNumberReader lineNumberReader;
    private Expression token;
    private int currentChar = ' ';

    /**
     * 提供给java config用于配置类
     */
    public static final class Builder {
        private Expression token;

        public Builder(){}

        public Tokenizer<Expression> build(){
            return new DefaultTokenizer(this);
        }

        public Builder token(Expression token){
            this.token = token;
            return this;
        }
    }

    /**
     * 提供默认构造函数
     * 初始化资源
     */
    public DefaultTokenizer(){
//        this.lineNumberReader = new LineNumberReader(new BufferedReader(Application.getReader()));
    }

    //只能通过Builder调用
    private DefaultTokenizer(Builder builder){
        this.token = builder.token;
    }

    @Override
    public void init(Reader reader) {
        this.lineNumberReader = new LineNumberReader(new BufferedReader(reader));
    }

    @Override
    public void close() throws IOException {
        lineNumberReader.close();
    }

    @Override
    public Expression next(){
        try {
            StringBuffer sb = new StringBuffer();
            while (!isEOF()) {
                if (!Character.isWhitespace(getChar())) { //不是空白字符
                   if(getChar() == '"'){ //String
                       return nextString();
                   } else if (Delimiter.isDelimiter(getChar())) { //间隔符直接返回
                       Expression token = new DelimiterExpression(String.valueOf(getChar()));
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
                        Expression token = new DelimiterExpression(Constants.SPACE + String.valueOf(getChar()));
                        nextChar();
                        return token;
                    } else if (getChar() == '[') { //e.g. name [...
                        Expression token = new DelimiterExpression(Constants.SPACE + String.valueOf(getChar()));
                        nextChar();
                        return token;
                    } else if (getChar() == '{') { //e.g. name {...
                        Expression token = new DelimiterExpression(Constants.SPACE + String.valueOf(getChar()));
                        nextChar();
                        return  token;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ExpressionTypeMode.NIL;
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
    private Expression nextString() throws IOException {
        StringBuffer sb = new StringBuffer();
        nextChar(); //eat '"'
        while (getChar() != '"') {
            sb.append(getChar());
            nextChar();
        }
        nextChar(); //eat '"'
        return new StringExpression(sb.toString());
    }

    /**
     * e.g. 1234 12.23
     * @return
     * @throws IOException
     */
    private Expression nextNumber() throws IOException, Exceptions.NumberFormatException {
        StringBuffer sb = new StringBuffer();
        while (Character.isDigit(getChar()) || getChar() == '.') {
            sb.append(getChar());
            nextChar();
        }
        if (Character.isWhitespace(getChar()) || Delimiter.isDelimiter(getChar()) || isEOF()) {
            return new NumberExpression(sb.toString());
        } else {
            throw new Exceptions.NumberFormatException("Exceptions Number Format!");
        }

//        if (!Character.isWhitespace(getChar()) && !Delimiter.isDelimiter(getChar())) { //e.g. 123.45abc
//            throw new Exceptions.NumberFormatException("Exceptions Number Format!");
//        }
//        return new NumberExpression(sb.toString());
    }

    /**
     * e.g. name2, na-rt, sym+nnn...
     * @return
     * @throws IOException
     */
    private Expression nextSymbol() throws IOException {
        StringBuffer sb = new StringBuffer();
        while (!Character.isWhitespace(getChar()) && !Delimiter.isDelimiter(getChar()) && getChar() != '\uFFFF') {
            sb.append(getChar());
            nextChar();
        }
        return new SymbolExpression(sb.toString());
    }

}
