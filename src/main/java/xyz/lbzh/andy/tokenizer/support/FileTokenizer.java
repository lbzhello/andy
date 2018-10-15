package xyz.lbzh.andy.tokenizer.support;

import xyz.lbzh.andy.core.Definition;
import xyz.lbzh.andy.expression.ExpressionFactory;
import xyz.lbzh.andy.tokenizer.LineNumberToken;
import xyz.lbzh.andy.tokenizer.Token;
import xyz.lbzh.andy.tokenizer.Tokenizer;

import java.io.*;

public class FileTokenizer implements Tokenizer<Token> {
    private LineNumberReader lineNumberReader;
    private int currentChar = ' ';

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
        this.currentChar = ' ';
        lineNumberReader.close();
    }

    @Override
    public Token next(){
        try {
            StringBuffer sb = new StringBuffer();
            while (hasNext()) {
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
