package xyz.lius.andy.compiler.tokenizer;

import xyz.lius.andy.io.CharIter;

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
