package xyz.lius.andy.tokenizer;

import xyz.lius.andy.io.CharIter;

import java.io.Closeable;
import java.io.InputStream;
import java.io.Reader;
import java.util.Iterator;

public interface Tokenizer<T> {

    /**
     * Tokenizer初始化方法为Tokenizer提供数据源
     * @param iter 输入流
     */
    void init(CharIter iter);

    T current();

    T next();

    boolean hasNext();

    default int getLineNumber() {
        return 0;
    }
}
