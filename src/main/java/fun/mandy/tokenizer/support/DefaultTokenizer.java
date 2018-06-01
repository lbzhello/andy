package fun.mandy.tokenizer.support;

import fun.mandy.tokenizer.Tokenizer;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;

public class DefaultTokenizer implements Tokenizer<String> {
    private LineNumberReader lineNumberReader;
    @Override
    public void init(Reader reader) {
        this.lineNumberReader = new LineNumberReader(reader);
    }

    @Override
    public String nextToken(){
        try {
            System.out.println(lineNumberReader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public int hasNextToken(){
        return -1;
    }
}
