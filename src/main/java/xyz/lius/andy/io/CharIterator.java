package xyz.lius.andy.io;

import java.text.CharacterIterator;

/**
 * @see CharacterIterator
 */
public interface CharIterator {
    char DONE = '\uFFFF';

    void refresh();

    boolean hasNext();

    char current();

    char next();

    char previous();
}
