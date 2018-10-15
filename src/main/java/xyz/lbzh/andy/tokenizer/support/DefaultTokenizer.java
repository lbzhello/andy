package xyz.lbzh.andy.tokenizer.support;

import xyz.lbzh.andy.core.Definition;
import xyz.lbzh.andy.expression.ExpressionFactory;
import xyz.lbzh.andy.tokenizer.LineNumberToken;
import xyz.lbzh.andy.tokenizer.Tokenizer;

import java.io.*;

public class DefaultTokenizer implements Tokenizer<LineNumberToken> {
    private LineNumberReader lineNumberReader;
    private LineNumberToken token;
    private int currentChar = ' ';

    /**
     * 提供给java config用于配置类
     */
    public static final class Builder {
        private LineNumberToken token;

        public Builder(){}

        public Tokenizer<LineNumberToken> build(){
            return new DefaultTokenizer(this);
        }

        public Builder token(LineNumberToken token){
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
    public int getLineNumber() {
        return this.lineNumberReader.getLineNumber() + 1; //1 based
    }

    @Override
    public void close() throws IOException {
        lineNumberReader.close();
    }

    @Override
    public LineNumberToken next(){
        try {
            StringBuffer sb = new StringBuffer();
            while (!isEOF()) {
                if (!Character.isWhitespace(getChar())) { //不是空白字符
                   if(getChar() == '"'){ //String
                       return nextString();
                   } else if (Definition.isDelimiter(getChar())) { //间隔符直接返回
                       LineNumberToken token = ExpressionFactory.token(String.valueOf(getChar()), getLineNumber());
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
                        LineNumberToken token = ExpressionFactory.token(Definition.SPACE + String.valueOf(getChar()), getLineNumber());
                        nextChar();
                        return token;
                    } else if (getChar() == '[') { //e.g. name [...
                        LineNumberToken token = ExpressionFactory.token(Definition.SPACE + String.valueOf(getChar()), getLineNumber());
                        nextChar();
                        return token;
                    } else if (getChar() == '{') { //e.g. name {...
                        LineNumberToken token = ExpressionFactory.token(Definition.SPACE + String.valueOf(getChar()), getLineNumber());
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
    private LineNumberToken nextString() throws IOException {
        StringBuffer sb = new StringBuffer();
        nextChar(); //eat '"'
        while (getChar() != '"') {
            sb.append(getChar());
            nextChar();
        }
        nextChar(); //eat '"'
        return ExpressionFactory.string(sb.toString(), getLineNumber());
    }

    /**
     * e.g. 1234 12.23
     * @return
     * @throws IOException
     */
    private LineNumberToken nextNumber() throws IOException {
        StringBuffer sb = new StringBuffer();
        while (Character.isDigit(getChar()) || getChar() == '.') {
            sb.append(getChar());
            nextChar();
        }
        if (Character.isWhitespace(getChar()) || Definition.isDelimiter(getChar()) || isEOF()) {
            return ExpressionFactory.number(sb.toString(), getLineNumber());
        } else {
            throw new NumberFormatException("Line: " + getLineNumber());
        }

    }

    /**
     * e.g. name2, na-rt, sym+nnn...
     * @return
     * @throws IOException
     */
    private LineNumberToken nextSymbol() throws IOException {
        StringBuffer sb = new StringBuffer();
        while (!Character.isWhitespace(getChar()) && !Definition.isDelimiter(getChar()) && getChar() != '\uFFFF') {
            sb.append(getChar());
            nextChar();
        }
        return ExpressionFactory.symbol(sb.toString(), getLineNumber());
    }

}
