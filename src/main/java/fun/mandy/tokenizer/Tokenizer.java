package fun.mandy.tokenizer;

import java.io.Reader;

public interface Tokenizer<T> {
    void init(Reader reader);
    T nextToken();
    int hasNextToken();

}
