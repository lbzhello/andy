package fun.mandy.tokenizer;

import java.io.Closeable;
import java.io.InputStream;
import java.io.Reader;
import java.util.Iterator;

public interface Tokenizer<T> extends Iterator<T>,Closeable {

    /**
     * Tokenizer初始化方法为Tokenizer提供数据源
     * @param reader 输入流
     */
    void init(Reader reader);


}
