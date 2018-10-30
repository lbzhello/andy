package xyz.lbzh.andy.tokenizer.support;

import xyz.lbzh.andy.core.Definition;
import xyz.lbzh.andy.expression.ExpressionFactory;
import xyz.lbzh.andy.tokenizer.LineNumberToken;
import xyz.lbzh.andy.tokenizer.Token;
import xyz.lbzh.andy.tokenizer.TokenFlag;
import xyz.lbzh.andy.tokenizer.Tokenizer;

import java.io.*;

public class FileTokenizer implements Tokenizer<Token> {
    private LineNumberReader lineNumberReader;
    private int currentChar = ' ';
    private Token currentToken = Definition.HOF;

    @Override
    public void init(Reader reader) {
        try {
            this.lineNumberReader = new LineNumberReader(new BufferedReader(reader));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Token getToken() {
        return currentToken;
    }

    @Override
    public Reader getReader() {
        return this.lineNumberReader;
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
            while (hasNext()) {
                if (!Character.isWhitespace(getChar())) { //不是空白字符
                   if(getChar() == '"'){ //String
                       currentToken = nextString();
                       return currentToken;
                   } else if (Definition.isDelimiter(getChar())) { //间隔符直接返回
                       currentToken = Definition.getDelimiter(getChar());
                       nextChar(); //eat
                       return currentToken;
//                       Character c1 = getChar(),c2;
//                       nextChar(); //eat c1
//                       if (c1 == '=') {
//                           if ((c2 = getChar()) == '>' || c2 == '=') { // => or ==
//                               nextChar(); //eat '>' or '='
//                               return ExpressionFactory.symbol(c1.toString() + c2.toString(), getLineNumber());
//                           } else {
//                               return ExpressionFactory.symbol("=", getLineNumber());
//                           }
//                       } else if (c1 == '<') {
//                           if (Character.isWhitespace(getChar())){
//                               return ExpressionFactory.symbol(c1.toString(), getLineNumber());
//                           } else if ((c2 = getChar()) == '=') {
//                               nextChar(); //eat '='
//                               return ExpressionFactory.symbol("<=", getLineNumber());
//                           } else { //left angle bracket
//                               return TokenFlag.ANGLE_BRACKET;
//                           }
//                       } else if (c1 == '>') {
//                           if (getChar() == '=') {
//                               nextChar(); //eat '='
//                               return ExpressionFactory.symbol(">=", getLineNumber());
//                           } else {
//                               return ExpressionFactory.symbol(">", getLineNumber());
//                           }
//                       } else if (c1 == '!') {
//                           if (getChar() == '=') {
//                               nextChar(); //eat
//                               return ExpressionFactory.symbol("!=", getLineNumber());
//                           } else {
//                               return ExpressionFactory.symbol("!");
//                           }
//                       } else {
//                           return Definition.getDelimiter(c1);
//                       }
                   } else if (Character.isDigit(getChar())) { //number
                       currentToken = nextNumber();
                       return currentToken;
                   } else if (getChar() == '`') {
                       currentChar = ' '; //reset
                       return TokenFlag.BACK_QUOTE;
                   } else {
                       currentToken = nextSymbol();
                       return currentToken;
                   }
                } else { //空白字符
                    while (Character.isWhitespace(getChar())) {
                        nextChar(); //eat
                    }
                    if (getChar() == '(') {  //e.g. name {...
                        nextChar();
                        currentToken = TokenFlag.ROUND_BRACKET_FREE;
                        return currentToken;
                    } else if (getChar() == '[') { //e.g. name [...
                        nextChar();
                        currentToken = TokenFlag.SQUARE_BRACKET_FREE;
                        return currentToken;
                    } else if (getChar() == '{') { //e.g. name {...
                        nextChar();
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
        return !isEOF();
    }

    private boolean isEOF() {
        return currentChar == -1;
    }

    private char nextChar() {
        try {
            currentChar = lineNumberReader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
//        while (Character.isDigit(getChar()) || getChar() == '.') {
//            sb.append(getChar());
//            nextChar();
//        }
//        if (Character.isWhitespace(getChar()) || Definition.isDelimiter(getChar()) || isEOF()) {
//            return ExpressionFactory.number(sb.toString(), getLineNumber());
//        } else {
//            throw new NumberFormatException("Line: " + getLineNumber());
//        }
        while (getChar() == '.' || !Character.isWhitespace(getChar()) && !Definition.isDelimiter(getChar()) && getChar() != '\uFFFF') {
            sb.append(getChar());
            nextChar();
        }
        return ExpressionFactory.number(sb.toString(), getLineNumber());
    }

    /**
     * e.g. name2, na-rt, sym+nnn...
     * @return
     * @throws IOException
     */
    private Token nextSymbol() throws IOException {
        StringBuffer sb = new StringBuffer();
        if (getChar() == '<') { //judgement if it's template
            sb.append(getChar());
            nextChar(); //eat '<'
            if (Character.isWhitespace(getChar()) || getChar() == '=') {
                sb.append(getChar());
                nextChar(); //eat
                return ExpressionFactory.symbol(sb.toString().trim(), getLineNumber());
            } else {
                return TokenFlag.ANGLE_BRACKET;
            }
        } else {
            while (!Character.isWhitespace(getChar()) && !Definition.isDelimiter(getChar()) && getChar() != '\uFFFF') {
                sb.append(getChar());
                nextChar();
            }
            return ExpressionFactory.symbol(sb.toString(), getLineNumber());
        }
    }

}
