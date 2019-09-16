package xyz.lius.andy.io;

import java.text.CharacterIterator;

/**
 * @see CharacterIterator
 */
public interface CharIterator {
    char DONE = '\uFFFF';

    /**
     * 设置源文件来源
     * @param path
     */
    default void setSource(String path) {

    }

    // template method
    default void refresh() {

    }

    boolean hasNext();

    char current();

    char next();

    char previous();
}
