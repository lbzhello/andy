package fun.mandy.tokenizer;

import java.io.Reader;

public interface Tokenizer<T> {

    /**
     * Tokenizer初始化方法
     * @param reader 输入流
     */
    void init(Reader reader);

    /**
     * 执行资源回收等操作
     */
    void close();

    /**
     * 下一个token
     * @return
     */
    T nextToken();

    /**
     * 判断流是否结束
     * @return
     */
    boolean hasNextToken();

}
