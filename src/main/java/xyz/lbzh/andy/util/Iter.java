package xyz.lbzh.andy.util;

import java.text.CharacterIterator;
import java.util.List;

/**
 * @see CharacterIterator
 * @param <T>
 */
public interface Iter<T> {
    T current();
    T next();
    boolean hasNext();
}
