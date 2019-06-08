package xyz.lius.andy.io;

import java.text.CharacterIterator;

/**
 * @see CharacterIterator
 */
public interface CharIterator {
    char DONE = '\uFFFF';

    boolean hasNext();

    char current();

    char next();

    char previous();
}
